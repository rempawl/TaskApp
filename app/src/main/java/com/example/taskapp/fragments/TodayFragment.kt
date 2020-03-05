package com.example.taskapp.fragments

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.taskapp.MainActivity
import com.example.taskapp.adapters.ParentFragmentType
import com.example.taskapp.adapters.TaskListAdapter
import com.example.taskapp.adapters.TaskListAdapter.Companion.LANDSCAPE_COLUMN_COUNT
import com.example.taskapp.adapters.TaskListAdapter.Companion.PORTRAIT_COLUMN_COUNT
import com.example.taskapp.databinding.TodayFragmentBinding
import com.example.taskapp.viewmodels.TodayViewModel
import javax.inject.Inject

class TodayFragment : Fragment() {

    companion object {
        fun newInstance() = TodayFragment()
    }

    @Inject
    lateinit var viewModel: TodayViewModel


    @Inject
    lateinit var taskListAdapterFactory: TaskListAdapter.Factory
    private val taskAdapter: TaskListAdapter by lazy {
        taskListAdapterFactory.create(ParentFragmentType.TodayFragment)
    }

    private  var binding: TodayFragmentBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TodayFragmentBinding
            .inflate(inflater, container, false)
//        taskAdapter = TaskListAdapter(viewModel)
        setUpBinding()
        updateTaskList()
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.apply {
            todayTasks.adapter = null
            viewModel = null
        }
        binding = null
    }

    private fun setUpBinding() {
        binding?.apply {
            viewModel = this@TodayFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
            todayTasks.apply {
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

    private fun updateTaskList() {
        viewModel.tasks.observe(viewLifecycleOwner, Observer { tasks ->
            taskAdapter.submitList(tasks)
        })
    }


}
