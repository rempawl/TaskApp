package com.example.taskapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.taskapp.MainActivity
import com.example.taskapp.database.entities.Task
import com.example.taskapp.databinding.TaskDetailsFragmentBinding
import com.example.taskapp.di.viewModel
import com.example.taskapp.viewmodels.TaskDetailsViewModel

class TaskDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = TaskDetailsFragment()
    }

    //todo add NavArgs
    //todo add fragment to nav graph

    private val viewModel: TaskDetailsViewModel by viewModel {
        (activity as MainActivity)
            .appComponent.taskDetailsViewModelFactory
            .create(Task(name = "test"))
    }
    private lateinit var binding: TaskDetailsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TaskDetailsFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
