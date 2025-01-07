package com.kevin.balancebuddies

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

class LevelAdapter(
    private val levels: List<Int>,
    private val unlockedLevels: Int,
    private val onLevelClick: (Int) -> Unit
) : RecyclerView.Adapter<LevelAdapter.LevelViewHolder>() {

    inner class LevelViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val button: Button = view.findViewById(R.id.level_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LevelViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_level, parent, false)
        return LevelViewHolder(view)
    }

    override fun onBindViewHolder(holder: LevelViewHolder, position: Int) {
        val level = levels[position]
        holder.button.text = "Level $level"
        holder.button.isEnabled = level <= unlockedLevels
        holder.button.setOnClickListener { onLevelClick(level) }
    }

    override fun getItemCount() = levels.size
}
