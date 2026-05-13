package com.example.android_edu.habits

import com.github.javafaker.Faker
import kotlin.random.Random

class HabitRepository {

    var habits = mutableListOf<Habit>()

    init {
        val count = Random.nextInt(50, 150)
        val faker = Faker.instance()

        habits = (1..count).map {
            Habit(
                id = it.toLong().toString(),
                title = faker.book().title(),
                description = faker.book().genre(),
                color = faker.color().hex(),
                isDone = it % 2 == 0
            )
        }.toMutableList()
    }

    fun toggleHabitDone(habitId: String) {
        habits = habits.map { habit ->
            if (habit.id == habitId) {
                habit.copy(isDone = !habit.isDone)
            } else {
                habit
            }
        }.toMutableList()
    }
}