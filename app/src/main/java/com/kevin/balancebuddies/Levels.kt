package com.kevin.balancebuddies

object Levels {
    fun getLevelData(level: Int): LevelData {
        return when (level) {
            1 -> LevelData(
                walls = listOf(
                    GameWall(0f, 350f, 500f, 50f),
                    GameWall(500f, 350f, 50f, 400f),
                    GameWall(800f, 0f, 50f, 300f),
                    GameWall(800f, 800f, 50f, 1000f),
                    GameWall(500f, 500f, 600f, 50f),
                    GameWall(1100f, 300f, 50f, 500f),
                    GameWall(1100f, 400f, 700f, 50f),
                    GameWall(1800f, 250f, 50f, 400f),
                    GameWall(1500f, 700f, 50f, 1100f),
                    GameWall(1450f, 0f, 50f, 200f)
                ),
                buttons = listOf(),  // Add buttons if needed
                goal = GameGoal(x = 100f, y = 100f, width = 150f, height = 150f) // Goal position and size
            )
            2 -> LevelData(
                walls = listOf(
                    GameWall(200f, 400f, 500f, 50f),  // A wall from (200, 400) to (500, 450)
                    GameWall(800f, 700f, 250f, 50f)   // Another wall from (800, 700) to (1050, 750)
                ),
                buttons = listOf(),  // Add buttons if needed
                goal = GameGoal(x = 200f, y = 300f, width = 150f, height = 150f) // Goal position and size
            )
            else -> LevelData(walls = listOf(), buttons = listOf(), goal = null)
        }
    }
}

data class LevelData(val walls: List<GameWall>, val buttons: List<Button>, val goal: GameGoal?)

data class GameWall(val x: Float, val y: Float, val width: Float, val height: Float) {
    val left get() = x
    val top get() = y
    val right get() = x + width
    val bottom get() = y + height
}

data class Button(val x: Int, val y: Int, val action: String)

data class GameGoal(val x: Float, val y: Float, val width: Float, val height: Float)
