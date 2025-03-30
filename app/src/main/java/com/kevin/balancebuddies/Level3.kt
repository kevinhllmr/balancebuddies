package com.kevin.balancebuddies

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Level3 : Level() {
    override val walls = Levels.getLevelData(3).walls
    override val gameGoal = Levels.getLevelData(3).goal
    override val ballStartPosition: Pair<Float, Float> = Pair(2100f, 200f)

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
        canvas.drawText("Level 3", canvas.width - 40f, 50f, paint)
    }
}
