package com.example.fastafappgp.ui.cam

data class Detection(
    val detectionId: String,
    val classId: Int,
    val leftmost: List<Float>,
    val rightmost: List<Float>,
    val notifyPoint: List<Float>
)
