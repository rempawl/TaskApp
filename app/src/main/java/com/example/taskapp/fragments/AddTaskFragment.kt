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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AddTaskFragment : Fragment() {

    companion object {
        fun newInstance() = AddTaskFragment()
    }

    private val viewModel: AddTaskViewModel by viewModel {
        (activity as MainActivity).appComponent.addTaskViewModel
    }

//    private val disposables = CompositeDisposable()
    private var binding: AddTaskFragmentBinding? =null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddTaskFragmentBinding
            .inflate(inflater, container, false)
        setupBinding()


        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.apply{
            viewModel = null
            lifecycleOwner = null
        }
        binding = null
    }

    private fun setupBinding() {

        binding?.apply {
    //            disposables.add( taskName
    //                .textChanges().skipInitialValue().subscribeOn(Schedulers.computation())
    //                .subscribe { text -> Log.d(MainActivity.TAG,text.toString())}
    //            )
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@AddTaskFragment.viewModel
            addReminderBtn.setOnClickListener { navigateToAddReminder() }
            saveBtn.setOnClickListener { saveTask() }
        }
    }

    private fun saveTask() {
        CoroutineScope(Dispatchers.Main).launch { viewModel.saveTask()}
        findNavController().navigate(
            AddTaskFragmentDirections.actionNavigationAddTaskToNavigationMyTasks()
        )
    }

    private fun navigateToAddReminder() {

        findNavController().navigate(
            AddTaskFragmentDirections.navigationAddTaskToNavigationAddReminder(
                viewModel.getTask()
            )
        )
    }

}
