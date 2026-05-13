package com.example.android_edu

import android.app.Application
import com.example.android_edu.habits.HabitRepository

class App : Application() {

    val habitRepository = HabitRepository()
}
