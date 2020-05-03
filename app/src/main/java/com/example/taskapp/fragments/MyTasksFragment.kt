package com.example.taskapp.fragments

import android.content.Context
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
import com.example.taskapp.di.viewModel
import com.example.taskapp.viewmodels.MyTasksViewModel
import javax.inject.Inject


class MyTasksFragment : Fragment() {

    companion object {
        fun newInstance() =            MyTasksFragment()


    }

    val viewModel: MyTasksViewModel by viewModel {
        injectViewModel()
    }


    @Inject
    lateinit var taskListAdapterFactory: TaskListAdapter.Factory

    private  var binding: MyTasksFragmentBinding? = null

    private val taskListAdapter: TaskListAdapter by lazy {
        taskListAdapterFactory.create { task -> findNavController().navigate(
            MyTasksFragmentDirections.navigationMyTasksToNavigationTaskDetails(task.taskID)
        )}
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectMembers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MyTasksFragmentBinding.inflate(layoutInflater, container, false)
        return binding!!.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpLayout()
        updateTaskList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding!!.taskList.adapter = null
        binding = null

    }

    private fun injectMembers() {
        (activity as MainActivity).appComponent.inject(this)
    }


    private fun injectViewModel() = (activity as MainActivity).appComponent.myTasksViewModel

    private fun setUpLayout() {
        binding!!.taskList.apply {
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
        binding!!.addTaskBtn.setOnClickListener { navigateToAddTask() }
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
