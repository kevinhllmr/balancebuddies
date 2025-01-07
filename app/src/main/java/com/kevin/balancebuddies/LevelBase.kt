package com.kevin.balancebuddies

import android.graphics.Canvas

abstract class Level {
    abstract val walls: List<GameWall>   // List of GameWall objects
    abstract val gameGoal: GameGoal?     // A GameGoal object for each level
    abstract val ballStartPosition: Pair<Float, Float>  // Ball starting position

    abstract fun onLevelStart()
    abstract fun checkCollisions(ball: Ball): Boolean
    abstract fun draw(canvas: Canvas)

    fun checkBallCollisionWithWalls(ball: Ball): Boolean {
        for (wall in walls) {
            if (ball.x + ball.radius > wall.left &&
                ball.x - ball.radius < wall.right &&
                ball.y + ball.radius > wall.top &&
                ball.y - ball.radius < wall.bottom) {
                return true  // Return true if a collision is detected
            }
        }
        return false  // No collision detected
    }

    fun drawWalls(canvas: Canvas) {
        val paint = android.graphics.Paint().apply { color = android.graphics.Color.BLACK }
        for (wall in walls) {
            canvas.drawRect(wall.left, wall.top, wall.right, wall.bottom, paint)
        }
    }

    // Check if the ball reaches the goal
    fun checkBallReachedGoal(ball: Ball): Boolean {
        gameGoal?.let {
            // Check if the ball is completely inside the goal (falling into it like a hole)
            return ball.x - ball.radius > it.x &&
                    ball.x + ball.radius < it.x + it.width &&
                    ball.y - ball.radius > it.y &&
                    ball.y + ball.radius < it.y + it.height
        }
        return false  // Return false if no goal is set or the ball hasn't reached the goal
    }
}
