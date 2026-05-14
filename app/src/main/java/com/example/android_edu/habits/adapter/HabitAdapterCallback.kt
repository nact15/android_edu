package com.example.android_edu.habits.adapter

import com.example.android_edu.habits.state.HabitDvo


fun interface HabitAdapterCallback {

    fun onItemClick(item: HabitDvo)
    fun onItemLongClick() = Unit
}