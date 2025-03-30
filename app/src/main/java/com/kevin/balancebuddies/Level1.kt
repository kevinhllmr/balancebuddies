package com.kevin.balancebuddies

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Level1 : Level() {
    override val walls = Levels.getLevelData(1).walls
    override val gameGoal = Levels.getLevelData(1).goal
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

        val paint2 = Paint().apply {
            color = Color.BLACK
            textSize = 100f
            textAlign = Paint.Align.RIGHT
        }

        canvas.drawText("Level 1", canvas.width - 40f, 50f, paint)
        canvas.drawText("Avoid the Walls", 800f, canvas.height - 100f, paint2)
        canvas.drawText("Goal", canvas.width - 175f, 350f, paint2)
    }
}
