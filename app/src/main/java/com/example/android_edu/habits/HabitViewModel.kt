package com.example.android_edu.habits

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android_edu.data.repoository.HabitRepository
import com.example.android_edu.habits.state.HabitDvo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface HabitViewModelContract {
    val isLoading: StateFlow<Boolean>
    val habits: StateFlow<List<HabitDvo>>
    val completedHabits: StateFlow<List<HabitDvo>>
}


class HabitViewModel(
    private val habitRepository: HabitRepository = HabitRepository()
) : ViewModel(), HabitViewModelContract {

    override val isLoading = MutableStateFlow(true)
    override val habits = MutableStateFlow(emptyList<HabitDvo>())
    override val completedHabits = habits.map { it.filter { item -> item.isDone } }
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())


    init {
        loadHabits()
    }


    private fun loadHabits() {

        viewModelScope.launch {
            isLoading.update { true }

            delay(3500)

            val habitsDto = habitRepository.habits
            habits.update { habitsDto.map { HabitDvo.mapFromDto(it) } }

            isLoading.update { false }
        }
    }

    fun toggleHabitDone(habit: HabitDvo) {

        viewModelScope.launch {
            isLoading.update { true }

            val updatedHabit = habitRepository.updateItem(HabitDvo.mapToDto(habit), isDone = !habit.isDone)
            val updatedList = habits.value.map { if (it.id == updatedHabit.id) HabitDvo.mapFromDto(updatedHabit) else it }

            habits.update { updatedList }
            isLoading.update { false }
        }
    }
}