package com.kevin.balancebuddies

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import java.io.IOException
import java.util.UUID

class BluetoothClientActivity : Activity() {
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var serverDevice: BluetoothDevice
    private val myUuid: UUID = UUID.fromString("e0e1c5c0-5f56-4c22-b4e9-2f698d456b35")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_client)

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        val deviceAddress = intent.getStringExtra("device_address")
        serverDevice = bluetoothAdapter.getRemoteDevice(deviceAddress)

        connectToServer()
    }

    private fun connectToServer() {
        Thread {
            try {
                val socket: BluetoothSocket = serverDevice.createRfcommSocketToServiceRecord(myUuid)
                bluetoothAdapter.cancelDiscovery()
                socket.connect()

                Log.d("BluetoothClient", "Connected to server")
                // Handle communication
            } catch (e: IOException) {
                Log.e("BluetoothClient", "Connection failed: $e")
            }
        }.start()
    }
}
