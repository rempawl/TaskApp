package com.example.taskapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskapp.MainActivity
import com.example.taskapp.adapters.TodayListAdapter
import com.example.taskapp.databinding.TodayFragmentBinding
import com.example.taskapp.viewmodels.TodayViewModel
import javax.inject.Inject

class TodayFragment : Fragment() {

    companion object {
        fun newInstance() = TodayFragment()
    }

    @Inject
    lateinit var viewModel: TodayViewModel
//            by viewModel { (activity as MainActivity).appComponent
//        .todayViewModel}

    @Inject
    lateinit var taskAdapter : TodayListAdapter

    private lateinit var binding: TodayFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).appComponent.inject(this)
        binding = TodayFragmentBinding
            .inflate(inflater,container,false)
        setUpBinding()
        updateTaskList()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.apply {
            todayTasks.adapter = null
            viewModel = null
        }
    }

    private fun setUpBinding() {
        binding.apply {
            viewModel = this@TodayFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
            todayTasks.apply {
                adapter = taskAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
    }

    private fun updateTaskList(){
        viewModel.tasks.observe(viewLifecycleOwner, Observer {tasks->
            taskAdapter.submitList(tasks)
        })
    }


}
