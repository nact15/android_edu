package com.example.android_edu.habits

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android_edu.App
import com.example.android_edu.R
import com.example.android_edu.databinding.FragmentCompletedHabitsBinding
import com.example.android_edu.databinding.FragmentHabitListBinding
import kotlinx.coroutines.launch

class CompletedHabitsFragment : Fragment(R.layout.fragment_completed_habits) {

    private val viewModel by activityViewModels<HabitViewModel>()

    private var _binding: FragmentCompletedHabitsBinding? = null
    private val binding get() = _binding!!

    private val habitRepository: HabitRepository
        get() = (requireActivity().application as App).habitRepository

    private val adapter = HabitAdapter { habit, isDone ->
        viewModel.toggleHabitDone(habit = habit, isDone = isDone)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentCompletedHabitsBinding.bind(view)

        binding.habitRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.habitRecyclerView.adapter = adapter

        adapter.submitItems(
            habitRepository.habits
        )

        observeState()
    }

    private fun observeState() {

        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.uiState.collect { state ->

                    when (state) {

                        is HabitUiState.Loading -> {

                            binding.loadingContainer.isVisible = true
                        }

                        is HabitUiState.Content -> {

                            binding.loadingContainer.isVisible = false

                            adapter.submitItems(state.completedHabits)
                        }
                    }
                }
            }
        }
    }


    override fun onDestroyView() {
        binding.habitRecyclerView.adapter = null
        _binding = null
        super.onDestroyView()
    }
}