package com.example.android_edu.data.repoository

import com.example.android_edu.data.models.HabitDto
import com.github.javafaker.Faker
import kotlin.random.Random

class HabitRepository {

    var habits = mutableListOf<HabitDto>()

    init {
        val count = Random.nextInt(50, 150)
        val faker = Faker.instance()

        habits = (1..count).map {
            HabitDto(
                id = it.toLong().toString(),
                title = faker.book().title(),
                description = faker.book().genre(),
                color = faker.color().hex(),
                isDone = it % 2 == 0
            )
        }.toMutableList()
    }

    suspend fun updateItem(mapToDto: HabitDto, isDone: Boolean): HabitDto {
        // this is network request
        return mapToDto.copy(isDone = isDone)
    }
}