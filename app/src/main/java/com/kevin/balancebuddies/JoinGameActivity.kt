package com.kevin.balancebuddies

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import java.io.IOException
import java.util.*

class JoinGameActivity : Activity() {
    private val bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_join_game)

        val deviceAddress: String = intent.getStringExtra("device_address") ?: ""
        val device: BluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceAddress)

        Thread {
            try {
                val socket: BluetoothSocket = device.createRfcommSocketToServiceRecord(UUID.randomUUID())
                socket.connect()

                // Successfully connected, handle the connection
                Log.d("JoinGameActivity", "Connected to host")
                // You can start the game or communicate with the server here
            } catch (e: IOException) {
                Log.e("JoinGameActivity", "Error connecting to host", e)
                runOnUiThread {
                    Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }
}
