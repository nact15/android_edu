package com.example.android_edu.habits.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.android_edu.databinding.ItemHabitBinding
import com.example.android_edu.habits.state.HabitDvo

class HabitAdapter(
    private val callback: HabitAdapterCallback
) : ListAdapter<HabitDvo, HabitViewHolder>(DiffUtilCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): HabitViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemHabitBinding.inflate(layoutInflater, parent, false)
        return HabitViewHolder(binding, callback = callback)
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        currentList.getOrNull(position)?.let { holder.bind(it) }
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int, payloads: List<Any?>) {
        val item = currentList.getOrNull(position) ?: return
        when {
            payloads.isNotEmpty() -> (payloads.firstOrNull() as? Boolean)?.let { holder.bindCheckedChange(item) }
            else -> super.onBindViewHolder(holder, position, payloads)
        }
    }


    private class DiffUtilCallback : DiffUtil.ItemCallback<HabitDvo>() {
        override fun areItemsTheSame(p0: HabitDvo, p1: HabitDvo): Boolean = p0.id == p1.id
        override fun areContentsTheSame(p0: HabitDvo, p1: HabitDvo): Boolean = p0 == p1
        override fun getChangePayload(oldItem: HabitDvo, newItem: HabitDvo): Any {
            return oldItem.isDone == newItem.isDone
        }
    }
}