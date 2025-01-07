package com.kevin.balancebuddies

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothServerSocket
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import java.io.IOException
import java.util.UUID

class BluetoothServerActivity : Activity() {
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private val myUuid: UUID = UUID.fromString("e0e1c5c0-5f56-4c22-b4e9-2f698d456b35")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_server)

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (!bluetoothAdapter.isEnabled) {
            Toast.makeText(this, "Please enable Bluetooth", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        startServer()
    }

    private fun startServer() {
        Thread {
            try {
                val serverSocket: BluetoothServerSocket? =
                    bluetoothAdapter.listenUsingRfcommWithServiceRecord("BalanceBuddies", myUuid)
                val clientSocket: BluetoothSocket? = serverSocket?.accept()

                clientSocket?.let {
                    Log.d("BluetoothServer", "Connection established")
                    // Handle communication
                }
            } catch (e: IOException) {
                Log.e("BluetoothServer", "Error: $e")
            }
        }.start()
    }
}
