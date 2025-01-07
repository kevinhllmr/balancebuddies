package com.kevin.balancebuddies

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class Level2 : Level() {
    // Fetch walls and goal from Levels.kt
    override val walls = Levels.getLevelData(2).walls
    override val gameGoal = Levels.getLevelData(2).goal
    override val ballStartPosition: Pair<Float, Float> = Pair(600f, 200f)

    override fun onLevelStart() {
        // Initialize things specific to Level 2
    }

    override fun checkCollisions(ball: Ball): Boolean {
        return checkBallCollisionWithWalls(ball)
    }

    override fun draw(canvas: Canvas) {
        // Draw the walls and the goal
        drawWalls(canvas)

        // Draw the goal
        gameGoal?.let {
            val paint = Paint().apply { color = Color.GREEN }
            canvas.drawRoundRect(it.x, it.y, it.x + it.width, it.y + it.height, 100f, 100f, paint)
        }

        // Draw the level name in the top right corner
        val paint = Paint().apply {
            color = Color.BLACK
            textSize = 40f
            textAlign = Paint.Align.RIGHT
        }
        canvas.drawText("Level 2", canvas.width - 30f, 50f, paint)
    }
}
