package com.kevin.balancebuddies

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

data class Ball(var x: Float, var y: Float, val radius: Float) {
    fun move(dx: Float, dy: Float) {
        x += dx
        y += dy
    }

    fun draw(canvas: Canvas) {
        val paint = Paint().apply { color = Color.BLACK }
        canvas.drawCircle(x, y, radius, paint)
    }
}
