package com.example.taskapp.fragments.today

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.taskapp.MainActivity
import com.example.taskapp.adapters.TaskListAdapter
import com.example.taskapp.adapters.TaskListAdapter.Companion.LANDSCAPE_COLUMN_COUNT
import com.example.taskapp.adapters.TaskListAdapter.Companion.PORTRAIT_COLUMN_COUNT
import com.example.taskapp.data.Result
import com.example.taskapp.data.task.TaskMinimal
import com.example.taskapp.databinding.TodayFragmentBinding
import com.example.taskapp.di.viewModel
import com.example.taskapp.utils.autoCleared
import com.example.taskapp.viewmodels.TodayViewModel

class TodayFragment : Fragment() {

    companion object {
        fun newInstance() = TodayFragment()
    }

    private val appComponent by lazy {
        (activity as MainActivity).appComponent
    }

    private val viewModel: TodayViewModel by viewModel {
        appComponent.todayViewModel
    }


    private var taskAdapter: TaskListAdapter by autoCleared()
    private var binding: TodayFragmentBinding by autoCleared()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        taskAdapter = TaskListAdapter(onItemClickListener = { task -> navigateToTaskDetails(task) })

        binding = TodayFragmentBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpLayout()
        updateTaskList()
    }

    private fun setUpLayout() {
        binding.apply {
            addSpontaneousTasksBtn.setOnClickListener { showSpontaneousTaskDialog() }

            todayTasksList.apply {
                adapter = taskAdapter
                val columnCount = if (resources.configuration.orientation ==
                    Configuration.ORIENTATION_PORTRAIT
                ) {
                    PORTRAIT_COLUMN_COUNT
                } else {
                    LANDSCAPE_COLUMN_COUNT
                }
                layoutManager = GridLayoutManager(requireContext(), columnCount)
                setHasFixedSize(true)
            }
        }
    }

    private fun showSpontaneousTaskDialog() {
        AddSpontaneousTaskDialogFragment()
            .show(childFragmentManager, "")
    }

    private fun updateTaskList() {
        viewModel.tasks.observe(viewLifecycleOwner, { result ->
            result.takeIf { it.checkIfIsSuccessAndListOf<TaskMinimal>() }
                ?.let { submitResult(it as Result.Success<*>) }
        })
    }

    @Suppress("UNCHECKED_CAST")
    private fun submitResult(result: Result.Success<*>) {
        taskAdapter.submitList(result.data as List<TaskMinimal>)
    }

    private fun navigateToTaskDetails(task: TaskMinimal) {
        findNavController().navigate(
            TodayFragmentDirections
                .navigationTodayToNavigationTaskDetails(task.taskID)
        )
    }

}



