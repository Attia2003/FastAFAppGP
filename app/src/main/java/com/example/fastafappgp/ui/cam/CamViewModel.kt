
package com.example.fastafappgp.ui.cam

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fastafappgp.util.Constant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CamViewModel:ViewModel() {

    private val socketManager = SocketManager()

    private val _detectionResults = MutableStateFlow<List<Detection>>(emptyList())
    val detectionResults: StateFlow<List<Detection>> = _detectionResults

    init {
        observeDetections()
    }

    fun connectToServer(ip: String = Constant.ip, port: Int = Constant.port) {
        socketManager.connect(ip, port)
    }

    private fun observeDetections() {
        viewModelScope.launch {
            socketManager.detectionFlow.collectLatest { detections ->
                _detectionResults.emit(detections)
            }
        }
    }

    fun sendFrame(bitmap: Bitmap) {
        socketManager.sendFrame(bitmap)
    }

    override fun onCleared() {
        super.onCleared()
        socketManager.disconnect()
    }
}
