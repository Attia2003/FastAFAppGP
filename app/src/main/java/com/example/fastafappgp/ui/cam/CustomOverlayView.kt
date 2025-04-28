package com.example.fastafappgp.ui.cam

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.fastafappgp.R
import kotlin.math.min
class CustomOverlayView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    var detections: List<Detection> = emptyList()
        set(value) {
            field = value
            invalidate()
        }

    private val boxPaint = Paint().apply {
        color = Color.GREEN
        style = Paint.Style.STROKE
        strokeWidth = 4f
    }

    private val dotPaint = Paint().apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

    private val iconBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.drawable.ic_detect)

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (width == 0 || height == 0) return

        val scaleX = width / 640f
        val scaleY = height / 640f

        detections.forEach { detection ->

            val left = detection.leftmost[0] * scaleX
            val top = detection.leftmost[1] * scaleY
            val right = detection.rightmost[0] * scaleX
            val bottom = detection.rightmost[1] * scaleY

            canvas.drawRect(left, top, right, bottom, boxPaint)

            val notifyX = detection.notifyPoint[0] * scaleX
            val notifyY = detection.notifyPoint[1] * scaleY

            canvas.drawCircle(notifyX, notifyY, 10f, dotPaint)

            val halfWidth = iconBitmap.width / 2f
            val halfHeight = iconBitmap.height / 2f

            canvas.drawBitmap(
                iconBitmap,
                notifyX - halfWidth,
                notifyY - halfHeight,
                null
            )
        }
    }
}