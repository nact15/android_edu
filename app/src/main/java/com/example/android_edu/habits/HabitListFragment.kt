package com.example.android_edu.habits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.android_edu.databinding.FragmentHabitListBinding
import com.example.android_edu.habits.adapter.HabitAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class HabitListFragment : Fragment() {

    private val viewModel by activityViewModels<HabitViewModel>()

    private var _binding: FragmentHabitListBinding? = null
    private val binding get() = _binding!!

    private val type by lazy { arguments?.getSerializable("type") ?: HabitScreenType.ALL }

    private val adapter by lazy { HabitAdapter { viewModel.toggleHabitDone(habit = it) } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observeState()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHabitListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.habitRecyclerView.adapter = adapter
    }

    private fun observeState() = with(viewModel) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                (if (type == HabitScreenType.ALL) habits else completedHabits)
                    .onEach { adapter.submitList(it) }
                    .launchIn(this)
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                isLoading
                    .onEach { binding.loadingContainer.isVisible = it }
                    .launchIn(this)
            }
        }
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}