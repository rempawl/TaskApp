package com.example.taskapp.fragments

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.taskapp.MainActivity
import com.example.taskapp.adapters.ParentFragmentType
import com.example.taskapp.adapters.TaskListAdapter
import com.example.taskapp.adapters.TaskListAdapter.Companion.LANDSCAPE_COLUMN_COUNT
import com.example.taskapp.adapters.TaskListAdapter.Companion.PORTRAIT_COLUMN_COUNT
import com.example.taskapp.databinding.MyTasksFragmentBinding
import com.example.taskapp.viewmodels.MyTasksViewModel
import javax.inject.Inject


class MyTasksFragment : Fragment() {

    companion object {
        fun newInstance() =
            MyTasksFragment()


    }


    @Inject
    lateinit var viewModel: MyTasksViewModel
//            by viewModel {
//        (activity as MainActivity)
//            .appComponent.myTaskViewModel
//    }

    @Inject
    lateinit var taskListAdapterFactory: TaskListAdapter.Factory


    private val taskListAdapter: TaskListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        taskListAdapterFactory.create(ParentFragmentType.MyTasksFragment)
    }
    private var binding: MyTasksFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).appComponent.inject(this)

        binding = MyTasksFragmentBinding
            .inflate(inflater, container, false)
        setupBinding()
        updateTaskList()

        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.apply {
            taskList.adapter = null
            viewModel = null
        }
        binding = null
    }


    private fun setupBinding() {
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@MyTasksFragment.viewModel
            taskList.apply {
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
            addTaskBtn.setOnClickListener { navigateToAddTask() }
        }
    }

    private fun navigateToAddTask() {
        findNavController().navigate(
            MyTasksFragmentDirections.navigationMyTasksToNavigationAddTask()
        )
    }

    private fun updateTaskList() {
        viewModel.tasks.observe(viewLifecycleOwner, Observer { tasks ->
            taskListAdapter.submitList(tasks)
        })
    }


}
