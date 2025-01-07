package com.kevin.balancebuddies

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.SurfaceHolder
import android.view.SurfaceView

class GameView(context: Context, private val level: Int) : SurfaceView(context), SurfaceHolder.Callback {
    private val paint = Paint()

    init {
        holder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {}

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}

    override fun surfaceDestroyed(holder: SurfaceHolder) {}

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        canvas.drawColor(Color.WHITE)

        paint.color = Color.BLACK
        paint.textSize = 50f
        canvas.drawText("Level $level", width - 200f, 100f, paint)
    }
}