package com.example.taskapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taskapp.MainActivity
import com.example.taskapp.databinding.AddTaskFragmentBinding
import com.example.taskapp.di.viewModel
import com.example.taskapp.viewmodels.addTask.AddTaskViewModel


class AddTaskFragment : Fragment() {

    companion object {
        fun newInstance() = AddTaskFragment()
    }

    private val viewModel: AddTaskViewModel by viewModel {
        (activity as MainActivity).appComponent.addTaskViewModel
    }

    private lateinit var binding: AddTaskFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddTaskFragmentBinding
            .inflate(inflater, container, false)
        setupBinding()
        return binding.root
    }

    private fun setupBinding() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@AddTaskFragment.viewModel
            addReminderBtn.setOnClickListener { navigateToAddReminder() }
            saveBtn.setOnClickListener { saveTask() }
        }
    }

    private fun saveTask() {
        viewModel.saveTask()
        findNavController().navigate(
            AddTaskFragmentDirections.actionNavigationAddTaskToNavigationHome()
                .setWasTaskAdded(true)
        )
    }

    private fun navigateToAddReminder() {

        findNavController().navigate(
            AddTaskFragmentDirections.navigationAddTaskToNavigationAddReminder(
                viewModel.createTaskDetails()
            )
        )
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
