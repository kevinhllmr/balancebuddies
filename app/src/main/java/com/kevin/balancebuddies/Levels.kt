package com.kevin.balancebuddies

object Levels {
    fun getLevelData(level: Int): LevelData {
        return when (level) {
            1 -> LevelData(
                walls = listOf(
                    GameWall(700f, 0f, 100f, 500f),
                    GameWall(1400f, 400f, 100f, 900f),
                ),
                buttons = listOf(),
                goal = GameGoal(x = 1900f, y = 450f, width = 150f, height = 150f)
            )

            2 -> LevelData(
                walls = listOf(
                    GameWall(450f, 0f, 75f, 800f),
                    GameWall(850f, 0f, 75f, 100f),
                    GameWall(950f, 450f, 75f, 800f),
                    GameWall(850f, 100f, 625f, 75f),
                    GameWall(1400f, 150f, 75f, 600f),
                    GameWall(1400f, 750f, 500f, 75f),
                    GameWall(1900f, 300f, 75f, 525f),
                ),
                buttons = listOf(),
                goal = GameGoal(x = 1600f, y = 450f, width = 150f, height = 150f)
            )

            3 -> LevelData(
                walls = listOf(
                    GameWall(0f, 350f, 500f, 50f),
                    GameWall(500f, 350f, 50f, 150f),
                    GameWall(500f, 350f, 50f, 150f),
                    GameWall(800f, 0f, 50f, 800f),
                    GameWall(300f, 800f, 550f, 50f),
                    GameWall(300f, 650f, 50f, 150f),
                    GameWall(1300f, 300f, 50f, 900f),
                    GameWall(1700f, 0f, 50f, 850f),
                ),
                buttons = listOf(),
                goal = GameGoal(x = 100f, y = 100f, width = 150f, height = 150f)
            )

            4 -> LevelData(
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
                buttons = listOf(),
                goal = GameGoal(x = 100f, y = 100f, width = 150f, height = 150f)
            )

            5 -> LevelData(
                walls = listOf(
                    //vertikal
                    GameWall(200f, 0f, 20f, 100f),
                    //GameWall(200f, 100f, 20f, 100f),
                    //GameWall(200f, 200f, 20f, 100f),
                    GameWall(200f, 300f, 20f, 100f),
                    GameWall(200f, 400f, 20f, 100f),
                    //GameWall(200f, 500f, 20f, 100f),
                    //GameWall(200f, 600f, 20f, 100f),
                    GameWall(200f, 700f, 20f, 100f),
                    GameWall(200f, 800f, 20f, 100f),
                    //GameWall(200f, 900f, 20f, 100f),
                    //GameWall(200f, 1000f, 20f, 100f),

                    GameWall(400f, 0f, 20f, 100f),
                    //GameWall(400f, 100f, 20f, 100f),
                    //GameWall(400f, 200f, 20f, 100f),
                    GameWall(400f, 300f, 20f, 100f),
                    GameWall(400f, 400f, 20f, 120f),
                    //GameWall(400f, 500f, 20f, 100f),
                    //GameWall(400f, 600f, 20f, 100f),
                    GameWall(400f, 700f, 20f, 100f),
                    GameWall(400f, 800f, 20f, 100f),
                    //GameWall(400f, 900f, 20f, 100f),
                    //GameWall(400f, 1000f, 20f, 100f),

                    GameWall(600f, 0f, 20f, 100f),
                    GameWall(600f, 100f, 20f, 100f),
                    GameWall(600f, 200f, 20f, 100f),
                    GameWall(600f, 300f, 20f, 100f),
                    GameWall(600f, 400f, 20f, 100f),
                    GameWall(600f, 500f, 20f, 100f),
                    GameWall(600f, 600f, 20f, 100f),
                    GameWall(600f, 700f, 20f, 100f),
                    GameWall(600f, 800f, 20f, 120f),
                    //GameWall(600f, 900f, 20f, 100f),
                    //GameWall(600f, 1000f, 20f, 100f),

                    GameWall(800f, 0f, 20f, 100f),
                    GameWall(800f, 100f, 20f, 100f),
                    GameWall(800f, 200f, 20f, 120f),
                    //GameWall(800f, 300f, 20f, 100f),
                    //GameWall(800f, 400f, 20f, 100f),
                    GameWall(800f, 500f, 20f, 100f),
                    GameWall(800f, 600f, 20f, 100f),
                    GameWall(800f, 700f, 20f, 100f),
                    GameWall(800f, 800f, 20f, 100f),
                    GameWall(800f, 900f, 20f, 100f),
                    GameWall(800f, 1000f, 20f, 100f),

                    //GameWall(1000f, 0f, 20f, 100f),
                    //GameWall(1000f, 100f, 20f, 100f),
                    //GameWall(1000f, 200f, 20f, 100f),
                    GameWall(1000f, 300f, 20f, 100f),
                    GameWall(1000f, 400f, 20f, 100f),
                    GameWall(1000f, 500f, 20f, 100f),
                    GameWall(1000f, 600f, 20f, 100f),
                    GameWall(1000f, 700f, 20f, 100f),
                    GameWall(1000f, 800f, 20f, 100f),
                    GameWall(1000f, 900f, 20f, 100f),
                    GameWall(1000f, 1000f, 20f, 100f),

                    GameWall(1200f, 0f, 20f, 100f),
                    //GameWall(1200f, 100f, 20f, 100f),
                    //GameWall(1200f, 200f, 20f, 100f),
                    GameWall(1200f, 300f, 20f, 100f),
                    GameWall(1200f, 400f, 20f, 120f),
                    //GameWall(1200f, 500f, 20f, 100f),
                    //GameWall(1200f, 600f, 20f, 100f),
                    //GameWall(1200f, 700f, 20f, 100f),
                    //GameWall(1200f, 800f, 20f, 100f),
                    //GameWall(1200f, 900f, 20f, 100f),
                    //GameWall(1200f, 1000f, 20f, 100f),

                    GameWall(1400f, 0f, 20f, 100f),
                    GameWall(1400f, 100f, 20f, 100f),
                    GameWall(1400f, 200f, 20f, 100f),
                    GameWall(1400f, 300f, 20f, 100f),
                    GameWall(1400f, 400f, 20f, 100f),
                    GameWall(1400f, 500f, 20f, 100f),
                    GameWall(1400f, 600f, 20f, 100f),
                    GameWall(1400f, 700f, 20f, 100f),
                    GameWall(1400f, 800f, 20f, 120f),
                    //GameWall(1400f, 900f, 20f, 100f),
                    //GameWall(1400f, 1000f, 20f, 100f),

                    GameWall(1600f, 0f, 20f, 100f),
                    GameWall(1600f, 100f, 20f, 100f),
                    GameWall(1600f, 200f, 20f, 100f),
                    GameWall(1600f, 300f, 20f, 100f),
                    GameWall(1600f, 400f, 20f, 100f),
                    GameWall(1600f, 500f, 20f, 100f),
                    GameWall(1600f, 600f, 20f, 100f),
                    GameWall(1600f, 700f, 20f, 100f),
                    GameWall(1600f, 800f, 20f, 120f),
                    //GameWall(1600f, 900f, 20f, 100f),
                    //GameWall(1600f, 1000f, 20f, 100f),

                    GameWall(1800f, 0f, 20f, 100f),
                    GameWall(1800f, 100f, 20f, 100f),
                    GameWall(1800f, 200f, 20f, 100f),
                    //GameWall(1800f, 300f, 20f, 100f),
                    //GameWall(1800f, 400f, 20f, 100f),
                    GameWall(1800f, 500f, 20f, 100f),
                    GameWall(1800f, 600f, 20f, 100f),
                    GameWall(1800f, 700f, 20f, 100f),
                    GameWall(1800f, 800f, 20f, 100f),
                    GameWall(1800f, 900f, 20f, 100f),
                    GameWall(1800f, 1000f, 20f, 100f),

                    GameWall(2000f, 0f, 20f, 100f),
                    GameWall(2000f, 100f, 20f, 100f),
                    GameWall(2000f, 200f, 20f, 100f),
                    //GameWall(2000f, 300f, 20f, 100f),
                    //GameWall(2000f, 400f, 20f, 100f),
                    //GameWall(2000f, 500f, 20f, 100f),
                    //GameWall(2000f, 600f, 20f, 100f),
                    //GameWall(2000f, 700f, 20f, 100f),
                    //GameWall(2000f, 800f, 20f, 100f),
                    //GameWall(2000f, 900f, 20f, 100f),
                    //GameWall(2000f, 1000f, 20f, 100f),

                    //GameWall(2200f, 0f, 20f, 100f),
                    GameWall(2200f, 100f, 20f, 100f),
                    GameWall(2200f, 200f, 20f, 100f),
                    GameWall(2200f, 300f, 20f, 100f),
                    GameWall(2200f, 400f, 20f, 100f),
                    GameWall(2200f, 500f, 20f, 100f),
                    GameWall(2200f, 600f, 20f, 100f),
                    GameWall(2200f, 700f, 20f, 100f),
                    GameWall(2200f, 800f, 20f, 100f),
                    GameWall(2200f, 900f, 20f, 100f),
                    GameWall(2200f, 1000f, 20f, 100f),






                    //horizontal
                    //GameWall(0f, 100f, 100f, 20f),
                    //GameWall(100f, 100f, 100f, 20f),
                    GameWall(200f, 100f, 100f, 20f),
                    GameWall(300f, 100f, 100f, 20f),
                    GameWall(400f, 100f, 100f, 20f),
                    GameWall(500f, 100f, 100f, 20f),
                    GameWall(600f, 100f, 100f, 20f),
                    GameWall(700f, 100f, 100f, 20f),
                    //GameWall(800f, 100f, 100f, 20f),
                    //GameWall(900f, 100f, 100f, 20f),
                    //GameWall(1000f, 100f, 100f, 20f),
                    //GameWall(1100f, 100f, 100f, 20f),
                    GameWall(1200f, 100f, 100f, 20f),
                    GameWall(1300f, 100f, 100f, 20f),
                    GameWall(1400f, 100f, 100f, 20f),
                    GameWall(1500f, 100f, 100f, 20f),
                    GameWall(1600f, 100f, 100f, 20f),
                    GameWall(1700f, 100f, 100f, 20f),
                    GameWall(1800f, 100f, 100f, 20f),
                    GameWall(1900f, 100f, 100f, 20f),
                    GameWall(2000f, 100f, 100f, 20f),
                    GameWall(2100f, 100f, 100f, 20f),

                    GameWall(0f, 300f, 100f, 20f),
                    GameWall(100f, 300f, 100f, 20f),
                    GameWall(200f, 300f, 100f, 20f),
                    GameWall(300f, 300f, 100f, 20f),
                    //GameWall(400f, 300f, 100f, 20f),
                    //GameWall(500f, 300f, 100f, 20f),
                    GameWall(600f, 300f, 100f, 20f),
                    GameWall(700f, 300f, 100f, 20f),
                    //GameWall(800f, 300f, 100f, 20f),
                    //GameWall(900f, 300f, 100f, 20f),
                    GameWall(1000f, 300f, 100f, 20f),
                    GameWall(1100f, 300f, 100f, 20f),
                    //GameWall(1200f, 300f, 100f, 20f),
                    //GameWall(1300f, 300f, 100f, 20f),
                    GameWall(1400f, 300f, 100f, 20f),
                    GameWall(1500f, 300f, 100f, 20f),
                    //GameWall(1600f, 300f, 100f, 20f),
                    //GameWall(1700f, 300f, 100f, 20f),
                    GameWall(1800f, 300f, 100f, 20f),
                    GameWall(1900f, 300f, 100f, 20f),
                    GameWall(2000f, 300f, 100f, 20f),
                    GameWall(2100f, 300f, 100f, 20f),

                    GameWall(0f, 500f, 100f, 20f),
                    GameWall(100f, 500f, 100f, 20f),
                    GameWall(200f, 500f, 100f, 20f),
                    GameWall(300f, 500f, 100f, 20f),
                    //GameWall(400f, 500f, 100f, 20f),
                    //GameWall(500f, 500f, 100f, 20f),
                    //GameWall(600f, 500f, 100f, 20f),
                    //GameWall(700f, 500f, 100f, 20f),
                    GameWall(800f, 500f, 100f, 20f),
                    GameWall(900f, 500f, 100f, 20f),
                    GameWall(1000f, 500f, 100f, 20f),
                    GameWall(1100f, 500f, 100f, 20f),
                    //GameWall(1200f, 500f, 100f, 20f),
                    //GameWall(1300f, 500f, 100f, 20f),
                    GameWall(1400f, 500f, 100f, 20f),
                    GameWall(1500f, 500f, 100f, 20f),
                    //GameWall(1600f, 500f, 100f, 20f),
                    //GameWall(1700f, 500f, 100f, 20f),
                    GameWall(1800f, 500f, 100f, 20f),
                    GameWall(1900f, 500f, 100f, 20f),
                    //GameWall(2000f, 500f, 100f, 20f),
                    //GameWall(2100f, 500f, 100f, 20f),

                    //GameWall(0f, 700f, 100f, 20f),
                    //GameWall(100f, 700f, 100f, 20f),
                    GameWall(200f, 700f, 100f, 20f),
                    GameWall(300f, 700f, 100f, 20f),
                    GameWall(400f, 700f, 100f, 20f),
                    GameWall(500f, 700f, 100f, 20f),
                    //GameWall(600f, 700f, 100f, 20f),
                    //GameWall(700f, 700f, 100f, 20f),
                    GameWall(800f, 700f, 100f, 20f),
                    GameWall(900f, 700f, 100f, 20f),
                    //GameWall(1000f, 700f, 100f, 20f),
                    //GameWall(1100f, 700f, 100f, 20f),
                    //GameWall(1200f, 700f, 100f, 20f),
                    //GameWall(1300f, 700f, 100f, 20f),
                    GameWall(1400f, 700f, 100f, 20f),
                    GameWall(1500f, 700f, 100f, 20f),
                    //GameWall(1600f, 700f, 100f, 20f),
                    //GameWall(1700f, 700f, 100f, 20f),
                    //GameWall(1800f, 700f, 100f, 20f),
                    //GameWall(1900f, 700f, 100f, 20f),
                    GameWall(2000f, 700f, 100f, 20f),
                    GameWall(2100f, 700f, 100f, 20f),

                    //GameWall(0f, 900f, 100f, 20f),
                    //GameWall(100f, 900f, 100f, 20f),
                    GameWall(200f, 900f, 100f, 20f),
                    GameWall(300f, 900f, 100f, 20f),
                    GameWall(400f, 900f, 100f, 20f),
                    GameWall(500f, 900f, 100f, 20f),
                    //GameWall(600f, 900f, 100f, 20f),
                    //GameWall(700f, 900f, 100f, 20f),
                    GameWall(800f, 900f, 100f, 20f),
                    GameWall(900f, 900f, 100f, 20f),
                    //GameWall(1000f, 900f, 100f, 20f),
                    //GameWall(1100f, 900f, 100f, 20f),
                    GameWall(1200f, 900f, 100f, 20f),
                    GameWall(1300f, 900f, 100f, 20f),
                    GameWall(1400f, 900f, 100f, 20f),
                    GameWall(1500f, 900f, 100f, 20f),
                    //GameWall(1600f, 900f, 100f, 20f),
                    //GameWall(1700f, 900f, 100f, 20f),
                    //GameWall(1800f, 900f, 100f, 20f),
                    //GameWall(1900f, 900f, 100f, 20f),
                    //GameWall(2000f, 900f, 100f, 20f),
                    //GameWall(2100f, 900f, 100f, 20f),
                    ),
                buttons = listOf(),
                goal = GameGoal(x = 2000f, y = 850f, width = 150f, height = 150f)
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
