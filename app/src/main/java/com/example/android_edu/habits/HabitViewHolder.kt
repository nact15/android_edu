package com.example.android_edu.habits

import androidx.recyclerview.widget.RecyclerView
import com.example.android_edu.databinding.ItemHabitBinding
import androidx.core.graphics.toColorInt

class HabitViewHolder(
    private val binding: ItemHabitBinding,
    private val onHabitCheckedChange: (Habit, Boolean) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Habit) {
        binding.titleText.text = item.title
        binding.descriptionText.text = item.description
        binding.avatarColorView.setBackgroundColor(item.color.toColorInt())

        binding.isDoneCheckBox.setOnCheckedChangeListener(null)
        binding.isDoneCheckBox.isChecked = item.isDone

        binding.isDoneCheckBox.setOnClickListener {
            onHabitCheckedChange(item, binding.isDoneCheckBox.isChecked)
        }
    }
}