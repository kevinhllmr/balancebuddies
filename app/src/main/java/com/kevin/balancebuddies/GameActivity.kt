package com.kevin.balancebuddies

import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Log.e
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlin.concurrent.thread
import java.io.IOException

@SuppressLint("MissingPermission")
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

    private var isMultiplayer = false
    private var isHost = false
    private var playerControl = "both"

    private lateinit var wifiP2pManager: WifiP2pManager
    private lateinit var wifiP2pChannel: WifiP2pManager.Channel
    private lateinit var receiver: WifiDirectReceiver

    private lateinit var progressBar: ProgressBar

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    private val requiredPermissions = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION,
        android.Manifest.permission.ACCESS_WIFI_STATE,
        android.Manifest.permission.CHANGE_WIFI_STATE,
        android.Manifest.permission.ACCESS_NETWORK_STATE,
        android.Manifest.permission.INTERNET
    )

    private val requestCodePermissions = 101

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!hasPermissions(this, *requiredPermissions)) {
            ActivityCompat.requestPermissions(this, requiredPermissions, requestCodePermissions)
        }

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        setContentView(R.layout.activity_game)

        surfaceView = findViewById(R.id.game_surface)
        surfaceHolder = surfaceView.holder
        progressBar = findViewById(R.id.progressBar)

        val displayMetrics = resources.displayMetrics
        screenWidth = displayMetrics.widthPixels
        screenHeight = displayMetrics.heightPixels

        val mode = intent.getStringExtra("MODE")
        isMultiplayer = mode == "multiplayer"

        if (isMultiplayer) {
            val isHostFlag = intent.getBooleanExtra("IS_HOST", false)
            isHost = isHostFlag

            wifiP2pManager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
            wifiP2pChannel = wifiP2pManager.initialize(this, mainLooper, null)
            val peerListListener = WifiP2pManager.PeerListListener { peers ->
                if (peers.deviceList.isNotEmpty()) {
                    Log.d("GameActivity", "Updated peers received: ${peers.deviceList}")
                    showDeviceList(peers.deviceList.toList())
                } else {
                    Log.d("GameActivity", "Still no peers found")
                }
            }

            receiver = WifiDirectReceiver(wifiP2pManager, wifiP2pChannel, peerListListener)
            val intentFilter = IntentFilter().apply {
                addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
                addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
                addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
                addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION)
            }
            registerReceiver(receiver, intentFilter)

            if (isHost) {
                startWifiDirectServer()
            } else {
                if (this::wifiP2pManager.isInitialized) {
                    joinWifiDirectServer()
                } else {
                    Toast.makeText(this, "Wi-Fi Direct not initialized correctly", Toast.LENGTH_SHORT).show()
                }
            }

            playerControl = if (isHost) {
                "x"
            } else {
                "y"
            }

        } else {
            playerControl = "both"
        }

        val levelId = intent.getIntExtra("LEVEL", 1)
        level = when (levelId) {
            1 -> Level1()
            2 -> Level2()
            3 -> Level3()
            4 -> Level4()
            5 -> Level5()
            else -> Level1()
        }

        val (startX, startY) = level.ballStartPosition
        ball = Ball(startX, startY, 50f)

        level.onLevelStart()

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        if (accelerometer == null) {
            finish()
        }

        startRenderingThread()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == requestCodePermissions) {
            if (grantResults.any { it == PackageManager.PERMISSION_DENIED }) {
                Toast.makeText(this, "Permissions not granted", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Permissions granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startWifiDirectServer() {
        thread {
            try {
                wifiP2pManager.createGroup(wifiP2pChannel, object : WifiP2pManager.ActionListener {
                    override fun onSuccess() {
                        Toast.makeText(this@GameActivity, "Server started", Toast.LENGTH_SHORT).show()
                    }

                    override fun onFailure(reason: Int) {
                        Toast.makeText(this@GameActivity, "Failed to start server", Toast.LENGTH_SHORT).show()
                    }
                })

                wifiP2pManager.requestPeers(wifiP2pChannel) { peers ->
                    if (peers.deviceList.isNotEmpty()) {
                        val device = peers.deviceList.first()
                        val config = WifiP2pConfig()
                        config.deviceAddress = device.deviceAddress
                        wifiP2pManager.connect(wifiP2pChannel, config, object : WifiP2pManager.ActionListener {
                            override fun onSuccess() {
                                Toast.makeText(this@GameActivity, "Connected to peer", Toast.LENGTH_SHORT).show()
                            }

                            override fun onFailure(reason: Int) {
                                Toast.makeText(this@GameActivity, "Connection failed", Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                }

            } catch (e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this, "Error starting server", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun joinWifiDirectServer() {
        thread {
            try {
                discoverPeersWithRetry(maxRetries = 3, delayMillis = 3000)
            } catch (e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this@GameActivity, "Failed to connect to the server", Toast.LENGTH_SHORT).show()
                    progressBar.visibility = View.GONE
                }
            }
        }
    }

    private fun discoverPeersWithRetry(maxRetries: Int, delayMillis: Long) {
        var retryCount = 0

        fun attemptDiscovery() {
            wifiP2pManager.discoverPeers(wifiP2pChannel, object : WifiP2pManager.ActionListener {
                override fun onSuccess() {
                    runOnUiThread {
                        progressBar.visibility = View.VISIBLE
                    }
                    Log.d("GameActivity", "Peer discovery started successfully")
                }

                override fun onFailure(reason: Int) {
                    runOnUiThread {
                        Toast.makeText(this@GameActivity, "Failed to start peer discovery", Toast.LENGTH_SHORT).show()
                        progressBar.visibility = View.GONE
                    }
                    Log.d("GameActivity", "Peer discovery failed, reason: $reason")
                }
            })

            wifiP2pManager.requestPeers(wifiP2pChannel) { peers ->
                runOnUiThread {
                    if (peers.deviceList.isNotEmpty()) {
                        Log.d("GameActivity", "Peers discovered: ${peers.deviceList}")
                        showDeviceList(peers.deviceList.toList())
                        progressBar.visibility = View.GONE
                    } else {
                        if (retryCount < maxRetries) {
                            retryCount++
                            Log.d("GameActivity", "No peers found, retrying ($retryCount/$maxRetries)...")
                            Thread.sleep(delayMillis)
                            attemptDiscovery()
                        } else {
                            Toast.makeText(this@GameActivity, "No available peers to connect to", Toast.LENGTH_SHORT).show()
                            Log.d("GameActivity", "No available peers to connect to after $maxRetries retries")
                            progressBar.visibility = View.GONE
                        }
                    }
                }
            }
        }

        attemptDiscovery()
    }

    private fun showDeviceList(deviceList: List<WifiP2pDevice>) {
        val deviceNames = deviceList.map { it.deviceName }

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, deviceNames)
        val listView: ListView = findViewById(R.id.deviceListView)
        listView.adapter = adapter

        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedDevice = deviceList[position]
            connectToDevice(selectedDevice)
        }
    }

    private fun connectToDevice(device: WifiP2pDevice) {
        if (device.deviceAddress.isNotEmpty()) {
            val config = WifiP2pConfig().apply {
                deviceAddress = device.deviceAddress
            }

            wifiP2pManager.connect(wifiP2pChannel, config, object : WifiP2pManager.ActionListener {
                override fun onSuccess() {
                    runOnUiThread {
                        Toast.makeText(this@GameActivity, "Connected to ${device.deviceName}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(reason: Int) {
                    runOnUiThread {
                        Toast.makeText(this@GameActivity, "Connection failed to ${device.deviceName}", Toast.LENGTH_SHORT).show()
                    }
                }
            })
        } else {
            runOnUiThread {
                Toast.makeText(this@GameActivity, "Invalid device address", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val accelerometerReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent?.action == "com.kevin.balancebuddies.UPDATE_ACCELEROMETER") {
                val xAccel = intent.getFloatExtra("xAccel", 0f)
                val yAccel = intent.getFloatExtra("yAccel", 0f)

                when (playerControl) {
                    "x" -> {
                        dx = yAccel * 2
                        ball.move(dx, dy)
                    }
                    "y" -> {
                        dy = xAccel * 2
                        ball.move(dx, dy)
                    }
                    "both" -> {
                        dx = yAccel * 2
                        dy = xAccel * 2
                        ball.move(dx, dy)
                    }
                }
            }
        }
    }

    private fun startRenderingThread() {
        thread {
            while (isRunning) {
                updateCanvas()
                Thread.sleep(16)
            }
        }
    }

    private fun updateCanvas() {
        if (surfaceHolder.surface.isValid) {
            val canvas = surfaceHolder.lockCanvas()
            try {
                canvas.drawPaint(backgroundPaint)
                level.draw(canvas)
                canvas.drawCircle(ball.x, ball.y, ball.radius, ballPaint)

            } finally {
                surfaceHolder.unlockCanvasAndPost(canvas)
            }
        }
    }

    private fun sendAccelerometerDataToHost(xAccel: Float, yAccel: Float) {
        val intent = Intent("com.kevin.balancebuddies.UPDATE_ACCELEROMETER")
        intent.putExtra("xAccel", xAccel)
        intent.putExtra("yAccel", yAccel)
        intent.setPackage("com.kevin.balancebuddies")
        sendBroadcast(intent)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onResume() {
        super.onResume()
        isRunning = true
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
        val intentFilter = IntentFilter("com.kevin.balancebuddies.UPDATE_ACCELEROMETER")
        registerReceiver(accelerometerReceiver, intentFilter, RECEIVER_NOT_EXPORTED)
    }

    override fun onPause() {
        super.onPause()
        isRunning = false
        sensorManager.unregisterListener(this)
        unregisterReceiver(accelerometerReceiver)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        val xAccel = event.values[0]
        val yAccel = event.values[1]

        if (!isHost) {
            sendAccelerometerDataToHost(xAccel, yAccel)
        }

        when (playerControl) {
            "x" -> {
                dx = yAccel * 2
                ball.move(dx, dy)
            }
            "y" -> {
                dy = xAccel * 2
                ball.move(dx, dy)
            }
            "both" -> {
                dx = yAccel * 2
                dy = xAccel * 2
                ball.move(dx, dy)
            }
        }

        if (ball.x - ball.radius < 0) ball.x = ball.radius
        if (ball.x + ball.radius > screenWidth) ball.x = screenWidth - ball.radius
        if (ball.y - ball.radius < 0) ball.y = ball.radius
        if (ball.y + ball.radius > screenHeight) ball.y = screenHeight - ball.radius

        if (level.checkBallCollisionWithWalls(ball)) {
            val (startX, startY) = level.ballStartPosition
            ball.x = startX
            ball.y = startY
        }

        if (level.checkBallReachedGoal(ball)) {
            val currentLevel = intent.getIntExtra("LEVEL", 1)
            val maxLevels = 5
            val nextLevelId = if (currentLevel < maxLevels) currentLevel + 1 else 1

            val intent = Intent(this, GameActivity::class.java).apply {
                putExtra("LEVEL", nextLevelId)
            }
            startActivity(intent)
            finish()
        }
    }


    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun hasPermissions(context: Context, vararg permissions: String): Boolean {
        permissions.forEach {
            if (ContextCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        isRunning = false
        sensorManager.unregisterListener(this)


        try {
            unregisterReceiver(accelerometerReceiver)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }

        if (isMultiplayer) {
            try {
                unregisterReceiver(receiver)
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            }
        }

        if (isMultiplayer && isHost) {
            wifiP2pManager.removeGroup(wifiP2pChannel, object : WifiP2pManager.ActionListener {
                override fun onSuccess() {
                }

                override fun onFailure(reason: Int) {
                    e("GameActivity", "Failed to remove Wi-Fi Direct group: $reason")
                }
            })
        }
    }
}
