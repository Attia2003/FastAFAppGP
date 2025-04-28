package com.example.fastafappgp.ui.cam

data class DetectionResponse(
    val requestId: String,
    val timestamp: Float,
    val processingTimeMs: Float,
    val detections: List<Detection>,
    val error: String? = null
)
