package com.example.taskapp.fragments

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
import com.example.taskapp.databinding.MyTasksFragmentBinding
import com.example.taskapp.di.viewModel
import com.example.taskapp.utils.autoCleared
import com.example.taskapp.viewmodels.MyTasksViewModel


class MyTasksFragment : Fragment() {

    companion object {
        fun newInstance() = MyTasksFragment()


    }

    val viewModel: MyTasksViewModel by viewModel {
        injectViewModel()
    }


    private var binding: MyTasksFragmentBinding by autoCleared()

    private var taskListAdapter: TaskListAdapter by autoCleared()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectMembers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        taskListAdapter =
            TaskListAdapter(onItemClickListener = { task -> navigateToTaskDetails(task) })
        binding = MyTasksFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpLayout()
        updateTaskList()
    }

    private fun injectMembers() {
        (activity as MainActivity).appComponent.inject(this)
    }


    private fun injectViewModel() = (activity as MainActivity).appComponent.myTasksViewModel

    private fun setUpLayout() {
        binding.taskList.apply {
            adapter = taskListAdapter
            val columnCount = if (resources.configuration.orientation ==
                Configuration.ORIENTATION_PORTRAIT
            ) {
                PORTRAIT_COLUMN_COUNT
            } else {
                LANDSCAPE_COLUMN_COUNT
            }
            layoutManager = GridLayoutManager(requireContext(), columnCount)
            setHasFixedSize(false)
        }
        binding.addTaskBtn.setOnClickListener { navigateToAddTask() }
    }


    private fun navigateToAddTask() {
        findNavController().navigate(
            MyTasksFragmentDirections.navigationMyTasksToNavigationAddTask()
        )
    }

    private fun updateTaskList() {
        viewModel.result.observe(viewLifecycleOwner, { res ->
            res.takeIf { it.checkIfIsSuccessAndListOf<TaskMinimal>() }?.let {
                submitResult(it as Result.Success)
            }
        })
    }

    private fun submitResult(result: Result.Success<*>) {
        @Suppress("UNCHECKED_CAST")
        taskListAdapter.submitList(result.data as List<TaskMinimal>)

    }

    private fun navigateToTaskDetails(task: TaskMinimal) {
        findNavController().navigate(
            MyTasksFragmentDirections.navigationMyTasksToNavigationTaskDetails(task.taskID)
        )
    }


}
