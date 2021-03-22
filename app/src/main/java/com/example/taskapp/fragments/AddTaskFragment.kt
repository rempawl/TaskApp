package com.example.taskapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.taskapp.MainActivity
import com.example.taskapp.databinding.AddEditTaskFragmentBinding
import com.example.taskapp.di.viewModel
import com.example.taskapp.utils.autoCleared
import com.example.taskapp.utils.providers.DispatcherProvider
import com.example.taskapp.viewmodels.reminder.ReminderViewModel
import javax.inject.Inject


class AddTaskFragment : AddEditTaskFragment() {
    companion object {
        fun newInstance() = AddTaskFragment()
        const val END_DATE_DIALOG_TAG: String = "END DATE DIALOG"
        const val BEGINNING_DATE_DIALOG_TAG = "Beginning date Dialog"
    }

    private val appComponent by lazy {
        (activity as MainActivity).appComponent
    }
    override val viewModel by viewModel { appComponent.addTaskViewModel }

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    override var binding: AddEditTaskFragmentBinding by autoCleared()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddEditTaskFragmentBinding
            .inflate(inflater, container, false)

        setUpBinding()
        setupObservers(viewModel)
        return binding.root
    }

    override fun navigateToMyTasks() {
        findNavController().navigate(
            AddTaskFragmentDirections.navigationAddTaskToNavigationMyTasks()
        )
        viewModel.onSaveTaskFinished()
    }


    override fun setupObservers(viewModel: ReminderViewModel) {
        super.setupObservers(viewModel)
        viewModel.apply {

        }

    }


}


