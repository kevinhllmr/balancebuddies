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

        val singlePlayerButton: Button = findViewById(R.id.button_singleplayer)
        val joinAsFirstPlayer: Button = findViewById(R.id.button_multiplayer1)
        val joinAsSecondPlayer: Button = findViewById(R.id.button_multiplayer2)

        singlePlayerButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("MODE", "singleplayer")
            intent.putExtra("IS_HOST", false)
            startActivity(intent)
        }

        joinAsFirstPlayer.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("MODE", "multiplayer")
            intent.putExtra("IS_HOST", true)
            startActivity(intent)
        }

        joinAsSecondPlayer.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            intent.putExtra("MODE", "multiplayer")
            intent.putExtra("IS_HOST", false)
            startActivity(intent)
        }
    }
}