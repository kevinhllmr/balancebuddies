package com.kevin.balancebuddies

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import kotlin.concurrent.thread

class GameActivity : Activity(), SensorEventListener {
    private lateinit var sensorManager: SensorManager
    private var accelerometer: Sensor? = null
    private lateinit var ball: Ball
    private lateinit var level: Level
    private var dx = 0f
    private var dy = 0f
    private var screenWidth = 0
    private var screenHeight = 0
    private lateinit var surfaceView: SurfaceView
    private lateinit var surfaceHolder: SurfaceHolder

    private val backgroundPaint = Paint().apply { color = Color.WHITE }
    private val ballPaint = Paint().apply { color = Color.BLACK }
    private var isRunning = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Lock orientation to landscape
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE


        setContentView(R.layout.activity_game)

        surfaceView = findViewById(R.id.game_surface)
        surfaceHolder = surfaceView.holder

        val displayMetrics = resources.displayMetrics
        screenWidth = displayMetrics.widthPixels
        screenHeight = displayMetrics.heightPixels

        // Get level id from intent
        val levelId = intent.getIntExtra("LEVEL", 1)
        level = when (levelId) {
            1 -> Level1()
            2 -> Level2()
            else -> Level1()
        }

        // Get ball starting position from the level
        val (startX, startY) = level.ballStartPosition // Assuming the Level class has a ballStartPosition
        ball = Ball(startX, startY, 50f) // Set the ball position based on the level

        level.onLevelStart()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if (accelerometer == null) {
            finish() // Exit the activity if accelerometer is missing
        }

        startRenderingThread()
    }

    private fun startRenderingThread() {
        thread {
            while (isRunning) {
                updateCanvas()
                Thread.sleep(16) // Approx. 60 FPS (1000 ms / 60 frames)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        isRunning = true
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    override fun onPause() {
        super.onPause()
        isRunning = false
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        val xAccel = event.values[0] // Now controls vertical movement (up-down)
        val yAccel = event.values[1] // Now controls horizontal movement (left-right)

        dx = yAccel * 5 // Invert yAccel for correct horizontal movement (left-right)
        dy = xAccel * 5 // Invert xAccel for correct vertical movement (up-down)

        ball.move(dx, dy)

        // Screen edge collision handling
        if (ball.x - ball.radius < 0) ball.x = ball.radius
        if (ball.x + ball.radius > screenWidth) ball.x = screenWidth - ball.radius
        if (ball.y - ball.radius < 0) ball.y = ball.radius
        if (ball.y + ball.radius > screenHeight) ball.y = screenHeight - ball.radius

        // Wall collision handling
        if (level.checkBallCollisionWithWalls(ball)) {
            ball.move(-dx, -dy) // Reverse movement on collision
        }

        // Goal collision handling: If the ball reaches the goal
        if (level.checkBallReachedGoal(ball)) {
            // Level completed: Proceed to next level
            val nextLevelId = (intent.getIntExtra("LEVEL", 1) + 1) % 3 // Example logic to go from Level 1 to Level 2
            val intent = Intent(this, GameActivity::class.java).apply {
                putExtra("LEVEL", nextLevelId)
            }
            startActivity(intent)
            finish()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun updateCanvas() {
        val canvas = surfaceHolder.lockCanvas()
        if (canvas != null) {
            // Clear the screen
            canvas.drawPaint(backgroundPaint)

            // Draw level and ball
            level.draw(canvas)
            canvas.drawCircle(ball.x, ball.y, ball.radius, ballPaint)

            surfaceHolder.unlockCanvasAndPost(canvas)
        }
    }
}
