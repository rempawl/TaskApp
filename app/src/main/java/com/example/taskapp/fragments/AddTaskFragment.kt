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
import com.example.taskapp.databinding.AddTaskFragmentBinding
import com.example.taskapp.di.viewModel
import com.example.taskapp.utils.ReminderDialogFragmentsDisplayer
import com.example.taskapp.utils.VisibilityChanger
import com.example.taskapp.viewmodels.AddTaskViewModel
import com.example.taskapp.workers.AlarmCreator
import com.google.android.material.radiobutton.MaterialRadioButton
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

    private var binding: AddTaskFragmentBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddTaskFragmentBinding
            .inflate(inflater, container, false)
        setUpBinding()
        return binding!!.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpObservers()
//        val toolbar = (activity as MainActivity).findViewById<Toolbar>(R.id.toolbar)
//        toolbar.setNavigationIcon(R.drawable.ic_close_black_24dp)

    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding?.apply {
            viewModel = null
            lifecycleOwner = null
        }

        binding = null
    }

    private fun setupFrequencyLayout() {
        binding?.apply {
            frequencyRadioGroup.apply {
                setFrequencyButtonsVisibility(checkedRadioButtonId) //on rotation
                setOnCheckedChangeListener { _, id ->
                    onFrequencyRadioCheck(id)
                }
            }

            setDailyFrequencyBtn.setOnClickListener {
                ReminderDialogFragmentsDisplayer.showFrequencyPickerDialog(
                    this@AddTaskFragment.viewModel.frequencyModel,
                    childFragmentManager
                )
            }

            setDaysOfWeekBtn.setOnClickListener {
                ReminderDialogFragmentsDisplayer.showDaysOfWeekPickerDialog(
                    this@AddTaskFragment.viewModel.frequencyModel,
                    childFragmentManager
                )
            }
        }
    }

    private fun onFrequencyRadioCheck(id: Int) {
        val frequencyModel = viewModel.frequencyModel
        binding?.run {
            when (activity?.findViewById<MaterialRadioButton>(id)) {
                dailyFreqRadio -> {
                    frequencyModel.setDailyFrequency()
                }
                daysOfWeekRadio -> {
                    frequencyModel.setDaysOfWeekFrequency()
                }

                else -> throw NoSuchElementException("There is no matching button")
            }
        }
        setFrequencyButtonsVisibility(id)

    }

    /**
     * function responsible for changing visibility of buttons under RadioGroup depending
     * on current radio  checked
     */
    private fun setFrequencyButtonsVisibility(id: Int) {
        binding?.run {
            val allBtns = listOf(
                setDailyFrequencyBtn,
                setDaysOfWeekBtn
            )

            when (id) {
                dailyFreqRadio.id -> VisibilityChanger.changeViewsHelper(
                    listOf(setDailyFrequencyBtn),
                    allBtns
                )
                daysOfWeekRadio.id -> VisibilityChanger.changeViewsHelper(
                    listOf(setDaysOfWeekBtn),
                    allBtns
                )
                else -> throw NoSuchElementException("There is no matching button")
            }
        }
    }


    /**
     * function responsible for changing visibility of buttons under RadioGroup depending
     * on current radio  checked
     */
    private fun setDurationButtonsVisibility(id: Int) {
        binding?.run {
            val allBtns = listOf(
                setDurationDaysBtn,
                setEndDateBtn
            )
            when (id) {
                xDaysDurationRadio.id -> VisibilityChanger.changeViewsHelper(
                    listOf(setDurationDaysBtn),
                    allBtns
                )
                endDateRadio.id -> VisibilityChanger.changeViewsHelper(
                    listOf(setEndDateBtn),
                    allBtns
                )
                noEndDateRadio.id -> VisibilityChanger.changeViewsHelper(
                    null,
                    allBtns
                )
                else -> throw NoSuchElementException("There is no matching button")
            }
        }
    }

    private fun onDurationRadioChecked(id: Int) {
        val durationModel = viewModel.durationModel
        binding?.run {
            when (id) {
                xDaysDurationRadio.id -> {
                    durationModel.setDaysDurationState()
                }
                endDateRadio.id -> {
                    durationModel.setEndDateDurationState()
                }
                noEndDateRadio.id -> {
                    durationModel.setNoEndDateDurationState()
                }
                else -> throw NoSuchElementException("There is no matching button")
            }
            setDurationButtonsVisibility(id)
        }
    }


    private fun setUpDurationLayout() {
        binding?.apply {
            beginningDateBtn.setOnClickListener {
                ReminderDialogFragmentsDisplayer.showBegDatePickerDialog(
                    this@AddTaskFragment.viewModel.durationModel,
                    childFragmentManager
                )
            }
            setDurationDaysBtn.setOnClickListener {
                ReminderDialogFragmentsDisplayer.showDurationDaysPickerDialog(
                    this@AddTaskFragment.viewModel.durationModel,
                    childFragmentManager
                )
            }
            setEndDateBtn.setOnClickListener {
                ReminderDialogFragmentsDisplayer.showEndDatePickerDialog(
                    this@AddTaskFragment.viewModel.durationModel,
                    childFragmentManager
                )
            }
            durationRadioGroup.apply {
                setDurationButtonsVisibility(checkedRadioButtonId) //to show proper one on rotation
                setOnCheckedChangeListener { _, id ->
                    onDurationRadioChecked(id)
                }
            }
        }
    }

    private fun setUpBinding() {
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@AddTaskFragment.viewModel
            confirmButton.setOnClickListener { addTaskWithReminder() }

            setTimeOfNotification.setOnClickListener {
                ReminderDialogFragmentsDisplayer.showNotificationPickerDialog(
                    this@AddTaskFragment.viewModel.notificationModel,
                    childFragmentManager
                )
            }
        }
        setUpDurationLayout()
        setupFrequencyLayout()


    }


    private fun setUpObservers() {
        viewModel.apply {
            toastText.observe(viewLifecycleOwner, Observer { id ->
                if (id != null) {
                    Toast.makeText(context, getString(id), Toast.LENGTH_SHORT).show()
                }
            })

             // when _addedTask is not null then  notification alarm should be set
             addedTask.observe(viewLifecycleOwner, Observer { task ->
                if (task != null) setAlarm(task)
            })
        }

    }

    private fun setAlarm(task: DefaultTask) {
        context?.let { ctx ->
            AlarmCreator.setTaskNotificationAlarm(task, true, ctx)
        }
    }


    private fun addTaskWithReminder() {
        CoroutineScope(Dispatchers.Main).launch {  viewModel.saveTask() }

        findNavController().navigate(
            AddTaskFragmentDirections.navigationAddReminderToNavigationMyTasks()
        )
    }


}


