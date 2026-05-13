package com.example.android_edu.habits

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_edu.App
import com.example.android_edu.R
import com.example.android_edu.databinding.FragmentHabitListBinding

class HabitListFragment : Fragment(R.layout.fragment_habit_list) {

    private var _binding: FragmentHabitListBinding? = null
    private val binding get() = _binding!!

    private val habitRepository: HabitRepository
        get() = (requireActivity().application as App).habitRepository

    private val adapter = HabitAdapter { habit, _ ->
        toggleHabitDone(habitId = habit.id)
    }

    fun toggleHabitDone(habitId: String) = habitRepository.toggleHabitDone(habitId)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentHabitListBinding.bind(view)

        binding.habitRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.habitRecyclerView.adapter = adapter

        adapter.submitItems(
            habitRepository.habits
        )
    }

    override fun onDestroyView() {
        binding.habitRecyclerView.adapter = null
        _binding = null
        super.onDestroyView()
    }
}