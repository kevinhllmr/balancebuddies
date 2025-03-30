package com.kevin.balancebuddies

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.p2p.*
import android.util.Log

@SuppressLint("MissingPermission")
class WifiDirectReceiver(
    private val wifiP2pManager: WifiP2pManager,
    private val wifiP2pChannel: WifiP2pManager.Channel,
    private val peerListListener: WifiP2pManager.PeerListListener
) : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null) return

        when (intent.action) {
            WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                val state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
                if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                    Log.d("WifiDirectReceiver", "Wi-Fi Direct is enabled")
                } else {
                    Log.d("WifiDirectReceiver", "Wi-Fi Direct is disabled")
                }
            }

            WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                Log.d("WifiDirectReceiver", "Peer list changed, requesting updated peer list...")
                wifiP2pManager.requestPeers(wifiP2pChannel, peerListListener)
            }

            WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                val networkInfo = intent.getParcelableExtra<android.net.NetworkInfo>(WifiP2pManager.EXTRA_NETWORK_INFO)
                if (networkInfo != null && networkInfo.isConnected) {
                    wifiP2pManager.requestConnectionInfo(wifiP2pChannel) { info: WifiP2pInfo ->
                        if (info.groupFormed) {
                            if (info.isGroupOwner) {
                                Log.d("WifiDirectReceiver", "I am the group owner.")
                            } else {
                                Log.d("WifiDirectReceiver", "I am a client in the group.")
                            }
                        }
                    }
                }
            }

            WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {
                val device = intent.getParcelableExtra<WifiP2pDevice>(WifiP2pManager.EXTRA_WIFI_P2P_DEVICE)
                Log.d("WifiDirectReceiver", "Device status changed: ${device?.deviceName}")
            }
        }
    }
}
