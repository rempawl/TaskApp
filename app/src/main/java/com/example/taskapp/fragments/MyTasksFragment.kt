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
import com.example.taskapp.R
import com.example.taskapp.adapters.ParentFragmentType
import com.example.taskapp.adapters.TaskListAdapter
import com.example.taskapp.adapters.TaskListAdapter.Companion.LANDSCAPE_COLUMN_COUNT
import com.example.taskapp.adapters.TaskListAdapter.Companion.PORTRAIT_COLUMN_COUNT
import com.example.taskapp.viewmodels.MyTasksViewModel
import kotlinx.android.synthetic.main.add_spontaneous_tasks_fragment.task_list
import kotlinx.android.synthetic.main.my_tasks_fragment.*
import javax.inject.Inject


class MyTasksFragment : Fragment() {

    companion object {
        fun newInstance() =
            MyTasksFragment()


    }


    @Inject
    lateinit var viewModel: MyTasksViewModel

    @Inject
    lateinit var taskListAdapterFactory: TaskListAdapter.Factory


    private val taskListAdapter: TaskListAdapter by lazy(LazyThreadSafetyMode.NONE) {
        taskListAdapterFactory.create(ParentFragmentType.MyTasksFragment)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).appComponent.inject(this)

        return inflater.inflate(R.layout.my_tasks_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpLayout()
        updateTaskList()

    }

    override fun onDestroyView() {
        super.onDestroyView()

        task_list.adapter = null

    }


    private fun setUpLayout() {
        task_list.apply {
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
        add_task_btn.setOnClickListener { navigateToAddTask() }
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
