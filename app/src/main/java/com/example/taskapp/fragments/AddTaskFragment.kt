package com.example.taskapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.taskapp.MainActivity
import com.example.taskapp.database.entities.DefaultTask
import com.example.taskapp.databinding.AddEditTaskFragmentBinding
import com.example.taskapp.di.viewModel
import com.example.taskapp.utils.AlarmCreator
import com.example.taskapp.utils.bindingArranger.AddTaskBindingArranger
import com.example.taskapp.viewmodels.AddTaskViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddTaskFragment : Fragment() {

    companion object {
        fun newInstance() = AddTaskFragment()
        const val END_DATE_DIALOG_TAG: String = "END DATE DIALOG"
        const val BEGINNING_DATE_DIALOG_TAG = "Beginning date Dialog"
    }


    private val viewModel: AddTaskViewModel by viewModel {
        (activity as MainActivity).appComponent.addTaskViewModel
    }

    private var binding: AddEditTaskFragmentBinding? = null

    private var bindingOrganiser: AddTaskBindingArranger? = null

    private val addTask = View.OnClickListener {
        CoroutineScope(Dispatchers.Main).launch { viewModel.saveTask() }

        findNavController().navigate(
            AddTaskFragmentDirections.navigationAddReminderToNavigationMyTasks()
        )
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddEditTaskFragmentBinding
            .inflate(inflater, container, false)

        bindingOrganiser = AddTaskBindingArranger(binding!!, this, viewModel, addTask)

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

            toastText.observe(viewLifecycleOwner, Observer { id ->
                if (id != null) {
                    showToast(getString(id))
                }
            })



            // when _addedTask is not null then  notification alarm should be set
            shouldSetAlarm.observe(viewLifecycleOwner, Observer { task ->
                if (task != null) setAlarm(task)
            })
        }

    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }


    private fun setAlarm(task: DefaultTask) {
        context?.let { ctx ->
            AlarmCreator.setTaskNotificationAlarm(task, true, ctx)
        }
    }


}


