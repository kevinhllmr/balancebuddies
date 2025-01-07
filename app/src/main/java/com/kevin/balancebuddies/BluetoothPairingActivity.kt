package com.kevin.balancebuddies

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast

class BluetoothPairingActivity : Activity() {

    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var deviceListAdapter: ArrayAdapter<String>
    private lateinit var listView: ListView

    private val deviceList = mutableListOf<String>()
    private val pairedDevices = mutableListOf<BluetoothDevice>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bluetooth_pairing)

        listView = findViewById(R.id.device_list)
        deviceListAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, deviceList)
        listView.adapter = deviceListAdapter

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth not supported", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        if (!bluetoothAdapter.isEnabled) {
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, 1)
        }

        discoverDevices()
    }

    private fun discoverDevices() {
        // Register for Bluetooth device discovery event
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(deviceReceiver, filter)

        // Start discovery
        bluetoothAdapter.startDiscovery()
    }

    private val deviceReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (BluetoothDevice.ACTION_FOUND == action) {
                val device: BluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)!!
                val deviceName = device.name ?: "Unknown Device"
                deviceList.add(deviceName)
                pairedDevices.add(device)
                deviceListAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(deviceReceiver)
    }
}
