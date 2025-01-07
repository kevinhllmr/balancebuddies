package com.kevin.balancebuddies

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import java.io.IOException
import java.util.*

class HostGameActivity : Activity() {
    private lateinit var bluetoothAdapter: BluetoothAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_host_game)

        val bluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter

        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth not supported", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        Thread {
            // Create Bluetooth server socket for accepting incoming connections
            val serverSocket: BluetoothServerSocket? = try {
                bluetoothAdapter.listenUsingRfcommWithServiceRecord("BalanceBuddies", UUID.randomUUID())
            } catch (e: IOException) {
                Log.e("HostGameActivity", "Error creating server socket", e)
                null
            }

            serverSocket?.let {
                try {
                    val socket: BluetoothSocket = it.accept()
                    // Successfully connected, handle the connection
                    Log.d("HostGameActivity", "Connection established")
                    // You can start the game or communicate with the client here
                } catch (e: IOException) {
                    Log.e("HostGameActivity", "Error accepting connection", e)
                } finally {
                    try {
                        it.close()
                    } catch (e: IOException) {
                        Log.e("HostGameActivity", "Error closing server socket", e)
                    }
                }
            }
        }.start()
    }
}
