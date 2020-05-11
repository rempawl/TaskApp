package com.example.taskapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskapp.MainActivity
import com.example.taskapp.database.entities.DefaultTask
import com.example.taskapp.databinding.AddEditTaskFragmentBinding
import com.example.taskapp.di.viewModel
import com.example.taskapp.utils.alarmCreator.AlarmCreator
import com.example.taskapp.utils.bindingArranger.EditTaskBindingArranger
import com.example.taskapp.viewmodels.reminder.ReminderViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


class EditTaskFragment : Fragment() {

    companion object {
        fun newInstance() = EditTaskFragment()
    }

    private val appComponent by lazy {
        (activity as MainActivity).appComponent
    }
    private val viewModel: ReminderViewModel by viewModel {
        injectViewModel()
    }

    @Inject
    lateinit var alarmCreator: AlarmCreator

    private val args: EditTaskFragmentArgs by navArgs()

    private var binding: AddEditTaskFragmentBinding? = null

    private var bindingOrganiser: EditTaskBindingArranger? = null

    private val editTask: View.OnClickListener = View.OnClickListener {
        CoroutineScope(Dispatchers.Main).launch { viewModel.saveTask() }
        navigateToMyTasks()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectMembers()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddEditTaskFragmentBinding.inflate(inflater, container, false)

        bindingOrganiser = EditTaskBindingArranger(binding!!, this, viewModel, editTask)

        setUpObservers()

        return binding!!.root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        bindingOrganiser = null
    }


    private fun setUpObservers() {
        viewModel.toastText.observe(viewLifecycleOwner, Observer { id ->
            if (id != null) showToast(getString(id))
        })

        viewModel.addedTask.observe(viewLifecycleOwner, Observer { addedTask ->
            if(addedTask != null) setAlarm(addedTask)
        })



    }

    private fun setAlarm(addedTask: DefaultTask) {
        alarmCreator.setTaskNotificationAlarm(addedTask,isToday = true)
    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    private fun navigateToMyTasks() {
        findNavController().navigate(
            EditTaskFragmentDirections.navigationEditTaskToNavigationMyTasks()
        )
    }
    private fun injectViewModel() = appComponent.editTaskViewModelFactory.create(args.task)
    private fun injectMembers() {
        appComponent.inject(this)
    }


}
