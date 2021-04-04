package com.example.taskapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskapp.MainActivity
import com.example.taskapp.databinding.AddEditTaskFragmentBinding
import com.example.taskapp.utils.autoCleared
import com.example.taskapp.utils.providers.DispatcherProvider
import com.example.taskapp.viewmodels.reminder.ReminderViewModel
import javax.inject.Inject


class EditTaskFragment : AddEditTaskFragment() {

    companion object {
        fun newInstance() = EditTaskFragment()
    }

    private val appComponent by lazy {
        (activity as MainActivity).appComponent
    }

    override val viewModel by lazy {
        injectViewModel()
    }

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    private val args: EditTaskFragmentArgs by navArgs()

    override var binding: AddEditTaskFragmentBinding by autoCleared()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectMembers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddEditTaskFragmentBinding.inflate(inflater, container, false)

        setUpBinding()
        setupObservers(viewModel)
        return binding.root
    }

    override fun setUpBinding() {
        super.setUpBinding()
        binding.apply {
            taskName.isEnabled = false
            beginningDateBtn.isEnabled = false
        }

    }

    override fun navigateToMyTasks() {
        findNavController().navigate(EditTaskFragmentDirections.navigationEditTaskToNavigationMyTasks())
        viewModel.onSaveTaskFinished()
    }


    override fun setupObservers(viewModel: ReminderViewModel) {
        super.setupObservers(viewModel)
        viewModel.apply {
        }
    }


    private fun injectViewModel() = appComponent.editTaskViewModelFactory.create(args.task)
    private fun injectMembers() {
        appComponent.inject(this)
    }


}
