package com.kevin.balancebuddies

import android.graphics.Canvas

abstract class Level {
    abstract val walls: List<GameWall>
    abstract val gameGoal: GameGoal?
    abstract val ballStartPosition: Pair<Float, Float>

    abstract fun onLevelStart()
    abstract fun checkCollisions(ball: Ball): Boolean
    abstract fun draw(canvas: Canvas)

    fun checkBallCollisionWithWalls(ball: Ball): Boolean {
        for (wall in walls) {
            if (ball.x + ball.radius > wall.left &&
                ball.x - ball.radius < wall.right &&
                ball.y + ball.radius > wall.top &&
                ball.y - ball.radius < wall.bottom) {
                return true
            }
        }
        return false
    }

    fun drawWalls(canvas: Canvas) {
        val paint = android.graphics.Paint().apply { color = android.graphics.Color.BLACK }
        for (wall in walls) {
            canvas.drawRect(wall.left, wall.top, wall.right, wall.bottom, paint)
        }
    }

    fun checkBallReachedGoal(ball: Ball): Boolean {
        gameGoal?.let {
            return ball.x - ball.radius > it.x &&
                    ball.x + ball.radius < it.x + it.width &&
                    ball.y - ball.radius > it.y &&
                    ball.y + ball.radius < it.y + it.height
        }
        return false
    }
}
