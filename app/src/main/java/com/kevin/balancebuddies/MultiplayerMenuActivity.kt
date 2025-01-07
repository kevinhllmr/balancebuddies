package com.kevin.balancebuddies

import android.app.Activity
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MultiplayerMenuActivity : Activity() {

    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var deviceListAdapter: ArrayAdapter<String>
    private val deviceList = mutableListOf<String>()
    private val devices = mutableListOf<BluetoothDevice>()

    private val permissionRequestCode = 100 // Set an appropriate permission request code

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_multiplayer_menu)

        // Initialize BluetoothManager and BluetoothAdapter
        bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothAdapter = bluetoothManager.adapter

        // Ensure Bluetooth is supported and available
        if (bluetoothAdapter == null) {
            Toast.makeText(this, "Bluetooth not supported", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val scanButton: Button = findViewById(R.id.button_scan)
        val hostButton: Button = findViewById(R.id.button_host_game)
        val deviceListView: ListView = findViewById(R.id.device_list)

        deviceListAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, deviceList)
        deviceListView.adapter = deviceListAdapter

        scanButton.setOnClickListener {
            startDiscovery()
        }

        hostButton.setOnClickListener {
            val intent = Intent(this, HostGameActivity::class.java)
            startActivity(intent)
        }

        deviceListView.setOnItemClickListener { _, _, position, _ ->
            val selectedDevice = devices[position]
            val intent = Intent(this, JoinGameActivity::class.java)
            intent.putExtra("device_address", selectedDevice.address)
            startActivity(intent)
        }
    }

    private fun startDiscovery() {
        // Check permissions before starting Bluetooth discovery
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.ACCESS_FINE_LOCATION),
                permissionRequestCode)
            return
        }

        // Start Bluetooth discovery
        val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
        registerReceiver(deviceDiscoveryReceiver, filter)

        bluetoothAdapter.startDiscovery()
        Toast.makeText(this, "Scanning for devices...", Toast.LENGTH_SHORT).show()
    }

    private val deviceDiscoveryReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (BluetoothDevice.ACTION_FOUND == intent.action) {
                val device: BluetoothDevice? = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                device?.let {
                    if (!devices.contains(it)) {
                        devices.add(it)
                        deviceList.add("${it.name ?: "Unknown"} (${it.address})")
                        deviceListAdapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(deviceDiscoveryReceiver)
    }
}
