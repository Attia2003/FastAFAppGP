package com.example.fastafappgp.ui.cam
import android.graphics.Bitmap
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import okhttp3.*
import okio.ByteString
import org.json.JSONObject
import java.nio.ByteBuffer
import java.nio.ByteOrder

class SocketManager {

    private var webSocket: WebSocket? = null
    private val client = OkHttpClient()

    private val _detectionChannel = Channel<List<Detection>>(Channel.BUFFERED)
    val detectionFlow = _detectionChannel.receiveAsFlow()

    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    fun connect(ip: String, port: Int) {
        val request = Request.Builder()
            .url("ws://$ip:$port/ws/detect")

            .build()

        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d("SocketManageropoen ", "WebSocket connected to ws://$ip:$port/ws/detect")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d("SocketManager", "Received message: $text")
                CoroutineScope(Dispatchers.IO).launch {
                    val detections = parseDetections(text)
                    _detectionChannel.send(detections)
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e("SocketManager", "WebSocket failure: ${t.message}")
                response?.let {
                    Log.e("SocketManager", "Response: $it")
                }
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                webSocket.close(1000, null)
                Log.d("WebSocket", "Closing WebSocket")
            }
        })
    }

    fun sendFrame(bitmap: Bitmap) {
        val resizedBitmap = if (bitmap.width == 640 && bitmap.height == 640) {
            bitmap
        } else {
            Bitmap.createScaledBitmap(bitmap, 640, 640, true)
        }

        val byteArray = bitmapToByteArray(resizedBitmap)

        Log.d("framesending", "Sending frame of size: ${byteArray.size} bytes")

        webSocket?.send(ByteString.of(*byteArray))
    }

    fun disconnect() {
        webSocket?.close(1000, "Normal Closure")
        scope.cancel()
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = java.io.ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream)
        return stream.toByteArray()
    }

    private fun parseDetections(json: String): List<Detection> {
        return try {
            val obj = JSONObject(json)
            if (!obj.has("detections")) {

                Log.e("SocketManager", "No detections field in response: $json")
                return emptyList()
            }
            val detectionsArray = obj.getJSONArray("detections")
            (0 until detectionsArray.length()).map { i ->
                val detectionObj = detectionsArray.getJSONObject(i)
                Detection(
                    detectionId = detectionObj.getString("detection_id"),
                    classId = detectionObj.getInt("class_id"),
                    leftmost = detectionObj.getJSONArray("leftmost").let { array ->
                        List(array.length()) { array.getDouble(it).toFloat() }
                    },
                    rightmost = detectionObj.getJSONArray("rightmost").let { array ->
                        List(array.length()) { array.getDouble(it).toFloat() }
                    },
                    notifyPoint = detectionObj.getJSONArray("notify_point").let { array ->
                        List(array.length()) { array.getDouble(it).toFloat() }
                    }
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}