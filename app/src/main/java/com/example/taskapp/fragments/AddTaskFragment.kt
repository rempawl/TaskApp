package com.example.taskapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.taskapp.MainActivity
import com.example.taskapp.database.entities.task.DefaultTask
import com.example.taskapp.databinding.AddEditTaskFragmentBinding
import com.example.taskapp.di.viewModel
import com.example.taskapp.utils.alarmCreator.AlarmCreator
import com.example.taskapp.utils.bindingArranger.AddTaskBindingArranger
import com.example.taskapp.utils.providers.DispatcherProvider
import com.example.taskapp.viewmodels.AddTaskViewModel
import javax.inject.Inject


class AddTaskFragment : Fragment() {
    companion object {
        fun newInstance() = AddTaskFragment()
        const val END_DATE_DIALOG_TAG: String = "END DATE DIALOG"
        const val BEGINNING_DATE_DIALOG_TAG = "Beginning date Dialog"
    }

    private val appComponent by lazy {
        (activity as MainActivity).appComponent
    }
    private val viewModel: AddTaskViewModel by viewModel {
        appComponent.addTaskViewModel
    }

    @Inject
    lateinit var alarmCreator: AlarmCreator

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    private var binding: AddEditTaskFragmentBinding? = null

    private var bindingOrganiser: AddTaskBindingArranger? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddEditTaskFragmentBinding
            .inflate(inflater, container, false)

        bindingOrganiser = AddTaskBindingArranger(
            binding = binding!!, fragment = this, viewModel = viewModel
        )

        setUpObservers()

        return binding!!.root
    }


    override fun onDestroyView() {
        super.onDestroyView()

        bindingOrganiser = null
        binding = null
    }

    private fun setUpObservers() {
        viewModel.apply {

            toastText.observe(viewLifecycleOwner, { id ->
                if (id != null) {
                    showToast(getString(id))
                }
            })
            isConfirmBtnClicked.observe(viewLifecycleOwner) { isClicked -> if (isClicked) navigateToMyTasks() }

            // when _addedTask is not null then  notification alarm should be set
            shouldSetAlarm.observe(viewLifecycleOwner, { (shouldSet, task) ->
                if (shouldSet && task != null) setAlarm(task)
            })
        }

    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }


    private fun setAlarm(task: DefaultTask) {
        alarmCreator.setTaskNotificationAlarm(task, true)
    }

    private fun navigateToMyTasks() {
        findNavController().navigate(
            AddTaskFragmentDirections.navigationAddTaskToNavigationMyTasks()
        )
        viewModel.onSaveTaskFinished()
    }


}


