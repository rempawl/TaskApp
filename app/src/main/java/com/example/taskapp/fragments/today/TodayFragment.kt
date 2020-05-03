package com.example.taskapp.fragments.today

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
import com.example.taskapp.adapters.TaskListAdapter
import com.example.taskapp.adapters.TaskListAdapter.Companion.LANDSCAPE_COLUMN_COUNT
import com.example.taskapp.adapters.TaskListAdapter.Companion.PORTRAIT_COLUMN_COUNT
import com.example.taskapp.databinding.TodayFragmentBinding
import com.example.taskapp.di.viewModel
import com.example.taskapp.viewmodels.TodayViewModel
import kotlinx.android.synthetic.main.today_fragment.*
import javax.inject.Inject

class TodayFragment : Fragment() {

    companion object {
        fun newInstance() = TodayFragment()
    }

    private val appComponent by lazy {
        (activity as MainActivity).appComponent
    }

    val viewModel: TodayViewModel by viewModel {
        appComponent.todayViewModel
    }

    @Inject
    lateinit var taskListAdapterFactory: TaskListAdapter.Factory

    private val taskAdapter: TaskListAdapter by lazy {
        taskListAdapterFactory.create { task ->
            findNavController().navigate(
                TodayFragmentDirections
                    .navigationTodayToNavigationTaskDetails(task.taskID)
            )
        }
    }


    private var binding: TodayFragmentBinding? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = TodayFragmentBinding.inflate(inflater, container, false)

        return binding!!.root

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpLayout()
        updateTaskList()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding!!.todayTasksList.adapter = null
        binding = null
    }

    private fun setUpLayout() {
        add_spontaneous_tasks_btn.setOnClickListener {
            AddSpontaneousTaskDialogFragment().show(
                childFragmentManager,
                ""
            )
        }

        binding!!.todayTasksList.apply {
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


    private fun updateTaskList() {
        viewModel.tasks.observe(viewLifecycleOwner, Observer { tasks ->
            taskAdapter.submitList(tasks)
        })
    }
}



