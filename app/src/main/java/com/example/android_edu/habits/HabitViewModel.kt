package com.example.android_edu.habits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HabitViewModel : ViewModel() {
    private val habitRepository = HabitRepository()
    private val _uiState = MutableStateFlow<HabitUiState>(
        HabitUiState.Loading
    )

    val uiState = _uiState.asStateFlow()

    init {
        loadHabits()
    }

    private fun loadHabits() {

        viewModelScope.launch {
            _uiState.value = HabitUiState.Loading

            delay(3500)

            val habits = habitRepository.habits
            val completedHabits = habits.filter { it.isDone }

            _uiState.value =
                HabitUiState.Content(
                    habits = habits,
                    completedHabits = completedHabits
                )
        }
    }

    fun toggleHabitDone(
        habit: Habit,
        isDone: Boolean
    ) {

        updateContent { state ->

            val updatedHabits = state.habits.map { oldHabit ->

                if (oldHabit.id == habit.id) {
                    oldHabit.copy(isDone = isDone)
                } else {
                    oldHabit
                }
            }

            state.copy(
                habits = updatedHabits,
                completedHabits = updatedHabits.filter { it.isDone }
            )
        }
    }

    private inline fun updateContent(
        block: (HabitUiState.Content) -> HabitUiState.Content
    ) {

        val current = _uiState.value

        if (current is HabitUiState.Content) {
            _uiState.value = block(current)
        }
    }
}