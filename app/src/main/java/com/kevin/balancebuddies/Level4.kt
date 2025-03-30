package com.kevin.balancebuddies

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Level4 : Level() {
    override val walls = Levels.getLevelData(4).walls
    override val gameGoal = Levels.getLevelData(4).goal
    override val ballStartPosition: Pair<Float, Float> = Pair(300f, 500f)

    override fun onLevelStart() {
    }

    override fun checkCollisions(ball: Ball): Boolean {
        return checkBallCollisionWithWalls(ball)
    }

    override fun draw(canvas: Canvas) {
        drawWalls(canvas)

        gameGoal?.let {
            val paint = Paint().apply { color = Color.GREEN }
            canvas.drawRoundRect(it.x, it.y, it.x + it.width, it.y + it.height, 100f, 100f, paint)
        }

        val paint = Paint().apply {
            color = Color.BLACK
            textSize = 40f
            textAlign = Paint.Align.RIGHT
        }
        canvas.drawText("Level 4", canvas.width - 40f, 50f, paint)
    }
}
