package com.example.android_edu.habits.adapter

import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.example.android_edu.databinding.ItemHabitBinding
import com.example.android_edu.habits.state.HabitDvo

class HabitViewHolder(
    private val binding: ItemHabitBinding,
    private val callback: HabitAdapterCallback
) : RecyclerView.ViewHolder(binding.root) {

    var item: HabitDvo? = null


    init {
        binding.isDoneCheckBox.setOnCheckedChangeListener { _, isChecked ->
            item?.let { item ->
                if (item.isDone != isChecked) {
                    callback.onItemClick(item)
                }
            }
        }
    }


    fun bind(item: HabitDvo) = with(binding) {
        this@HabitViewHolder.item = item
        titleText.text = item.title
        descriptionText.text = item.description
        avatarColorView.setBackgroundColor(item.color.toColorInt())
        if (isDoneCheckBox.isChecked != item.isDone) {
            isDoneCheckBox.isChecked = item.isDone
        }
    }

    fun bindCheckedChange(item: HabitDvo) = with(binding) {
        if (isDoneCheckBox.isChecked != item.isDone) {
            isDoneCheckBox.isChecked = item.isDone
        }
    }
}