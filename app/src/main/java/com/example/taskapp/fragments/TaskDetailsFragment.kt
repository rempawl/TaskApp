package com.example.taskapp.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskapp.MainActivity

import com.example.taskapp.R
import com.example.taskapp.databinding.TaskDetailsFragmentBinding
import com.example.taskapp.di.viewModel
import com.example.taskapp.viewmodels.TaskDetailsViewModel

class TaskDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = TaskDetailsFragment()
    }

    //todo add NavArgs
    //todo add fragment to nav graph

    private val viewModel: TaskDetailsViewModel by viewModel { (activity as MainActivity)
        .appComponent.taskDetailsViewModel }
    private lateinit var binding : TaskDetailsFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TaskDetailsFragmentBinding.
            inflate(inflater,container,false)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}
