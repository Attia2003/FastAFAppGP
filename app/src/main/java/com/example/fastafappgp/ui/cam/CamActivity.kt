package com.example.fastafappgp.ui.cam

import android.Manifest
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.fastafappgp.Constant
import com.example.fastafappgp.databinding.ActivityCamBinding
import kotlinx.coroutines.*
import java.util.concurrent.Executors

class CamActivity : ComponentActivity() {

    private lateinit var binding: ActivityCamBinding
    private val viewModel: CamViewModel by viewModels()

    private val cameraExecutor = Executors.newSingleThreadExecutor()

    private var lastSentTime = 0L
    private val frameIntervalMs = 100L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCamBinding.inflate(layoutInflater)
        setContentView(binding.root)


        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 0)


        viewModel.connectToServer()

        startCamera()
        observeDetections()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.previewView.surfaceProvider)
                }

            val imageAnalyzer = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, { imageProxy ->
                        processFrame(imageProxy)
                    })
                }

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalyzer)
            } catch (e: Exception) {
                Log.e("CamActivity", "Camera binding failed", e)
            }

        }, ContextCompat.getMainExecutor(this))
    }

    private fun processFrame(imageProxy: ImageProxy) {
        val currentTime = System.currentTimeMillis()
        if (currentTime - lastSentTime >= frameIntervalMs) {
            val bitmap = imageProxy.toBitmap()
            if (bitmap != null) {
                viewModel.sendFrame(bitmap)
                lastSentTime = currentTime
            }
        }
        imageProxy.close()
    }

    private fun observeDetections() {
        lifecycleScope.launch {
                viewModel.detectionResults.collect { detections ->
                    binding.overlayView.detections = detections
                }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}
