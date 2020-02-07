package com.example.taskapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskapp.MainActivity
import com.example.taskapp.adapters.TaskListAdapter
import com.example.taskapp.databinding.MyTasksFragmentBinding
import com.example.taskapp.di.viewModel



class MyTasksFragment : Fragment() {

    companion object {
        fun newInstance() =
            MyTasksFragment()
    }

    private val viewModel by viewModel {
        (activity as MainActivity)
            .appComponent.myTaskViewModel
    }

    private lateinit var taskListAdapter: TaskListAdapter
    private lateinit var binding: MyTasksFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyTasksFragmentBinding
            .inflate(inflater, container, false)
        taskListAdapter = TaskListAdapter()
        setupBinding()
        updateTaskList()

        return binding.root
    }

    private fun setupBinding() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@MyTasksFragment.viewModel
            taskList.apply {
                adapter = taskListAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(false)
            }
            addTaskBtn.setOnClickListener { navigateToAddTask() }
        }
    }

    private fun navigateToAddTask() {
        findNavController().navigate(
            HomeViewPagerFragmentDirections.navigationHomeToNavigationAddTask()
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    private fun updateTaskList() {
        viewModel.tasks.observe(viewLifecycleOwner, Observer { tasks ->
            taskListAdapter.submitList(tasks)
        })
    }

}
