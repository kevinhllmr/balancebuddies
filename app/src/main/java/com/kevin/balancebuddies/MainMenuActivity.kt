package com.kevin.balancebuddies

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class MainMenuActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        actionBar?.hide()

        setContentView(R.layout.activity_main_menu)

        val singleplayerButton: Button = findViewById(R.id.button_singleplayer)
        val multiplayerButton: Button = findViewById(R.id.button_multiplayer)

        singleplayerButton.setOnClickListener {
            val intent = Intent(this, LevelSelectionActivity::class.java)
            startActivity(intent)
        }

        multiplayerButton.setOnClickListener {
            // Start the multiplayer menu where the user can either host or join a game
            val intent = Intent(this, MultiplayerMenuActivity::class.java)
            startActivity(intent)
        }
    }
}
