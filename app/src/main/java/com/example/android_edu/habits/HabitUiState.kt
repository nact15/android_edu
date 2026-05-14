package com.example.android_edu.habits

sealed interface HabitUiState {
    data object Loading : HabitUiState

    data class Content(
        val habits: List<Habit>,
        val completedHabits: List<Habit>
    ) : HabitUiState
}