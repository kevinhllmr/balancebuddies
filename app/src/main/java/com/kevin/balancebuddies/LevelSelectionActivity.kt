package com.kevin.balancebuddies

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class LevelSelectionActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_selection)

        val level1Button: Button = findViewById(R.id.level1_button)
        val level2Button: Button = findViewById(R.id.level2_button)

        level1Button.setOnClickListener {
            startLevel(1)
        }

        level2Button.setOnClickListener {
            startLevel(2)
        }
    }

    private fun startLevel(levelId: Int) {
        val intent = Intent(this, GameActivity::class.java)
        intent.putExtra("LEVEL", levelId)
        startActivity(intent)
    }
}
