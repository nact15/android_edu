package com.example.android_edu.habits.state

import com.example.android_edu.data.models.HabitDto

data class HabitDvo(
    val id: String,
    val title: String,
    val description: String,
    val color: String,
    val isDone: Boolean
) {

    companion object {

        fun mapFromDto(dto: HabitDto) = HabitDvo(
            id = dto.id,
            title = dto.title,
            description = dto.description,
            color = dto.color,
            isDone = dto.isDone
        )

        fun mapToDto(dvo: HabitDvo) = HabitDto(
            id = dvo.id,
            title = dvo.title,
            description = dvo.description,
            color = dvo.color,
            isDone = dvo.isDone
        )
    }
}