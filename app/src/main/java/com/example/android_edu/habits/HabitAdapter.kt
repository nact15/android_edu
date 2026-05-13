package com.example.android_edu.habits

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.android_edu.databinding.ItemHabitBinding

class HabitAdapter(
    private val onHabitCheckedChange: (Habit, Boolean) -> Unit
) : RecyclerView.Adapter<HabitViewHolder>() {
    private val items = mutableListOf<Habit>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        p1: Int
    ): HabitViewHolder {
        val binding = ItemHabitBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return HabitViewHolder(
            binding,
            onHabitCheckedChange = onHabitCheckedChange
        )
    }

    override fun onBindViewHolder(
        holder: HabitViewHolder,
        position: Int
    ) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size


    fun submitItems(newItems: List<Habit>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}