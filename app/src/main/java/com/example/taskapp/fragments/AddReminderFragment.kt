package com.example.taskapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskapp.MainActivity
import com.example.taskapp.database.entities.DefaultTask
import com.example.taskapp.databinding.AddReminderFragmentBinding
import com.example.taskapp.di.viewModel
import com.example.taskapp.fragments.reminder.*
import com.example.taskapp.utils.VisibilityChanger
import com.example.taskapp.viewmodels.reminder.ReminderViewModel
import com.example.taskapp.viewmodels.reminder.durationModel.DurationModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.FrequencyModel
import com.example.taskapp.viewmodels.reminder.notificationModel.NotificationModel
import com.example.taskapp.workers.AlarmCreator
import com.google.android.material.radiobutton.MaterialRadioButton

class AddReminderFragment : Fragment() {

    companion object {
        fun newInstance() = AddReminderFragment()
        const val END_DATE_DIALOG_TAG: String = "END DATE DIALOG"
        const val BEGINNING_DATE_DIALOG_TAG = "Beginning date Dialog"
    }


    private val viewModel: ReminderViewModel by viewModel {
        (activity as MainActivity).appComponent.addReminderViewModelFactory
            .create(args.task)
    }

    private var binding: AddReminderFragmentBinding? = null
    private val args: AddReminderFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = AddReminderFragmentBinding
            .inflate(inflater, container, false)

        return binding!!.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpBinding()
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
                    setFrequencyButtonsVisibility(id)
                }
            }

            setDailyFrequencyBtn.setOnClickListener {
                ReminderDialogFragmentsDisplayer.showFrequencyPickerDialog(
                    this@AddReminderFragment.viewModel.frequencyModel,
                    childFragmentManager
                )
            }

            setDaysOfWeekBtn.setOnClickListener {
                ReminderDialogFragmentsDisplayer.showDaysOfWeekPickerDialog(
                    this@AddReminderFragment.viewModel.frequencyModel,
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
        binding?.run{
            val allBtns = listOf(
                setDailyFrequencyBtn,
                setDaysOfWeekBtn
            )

            when (activity?.findViewById<MaterialRadioButton>(id)) {
                dailyFreqRadio -> VisibilityChanger.changeViewsHelper(
                    listOf(setDailyFrequencyBtn),
                    allBtns
                )
                daysOfWeekRadio -> VisibilityChanger.changeViewsHelper(
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
        binding?.run{
            val allBtns = listOf(
                setDurationDaysBtn,
                setEndDateBtn
            )
            when (activity?.findViewById<View>(id)!!) {
                xDaysDurationRadio -> VisibilityChanger.changeViewsHelper(
                    listOf(setDurationDaysBtn),
                    allBtns
                )
                endDateRadio -> VisibilityChanger.changeViewsHelper(
                    listOf(setEndDateBtn),
                    allBtns
                )
                noEndDateRadio -> VisibilityChanger.changeViewsHelper(
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
            when (activity?.findViewById<View>(id)!!) {
                xDaysDurationRadio -> {
                    durationModel.setDaysDurationState()
                }
                endDateRadio -> {
                    durationModel.setEndDateDurationState()
                }
                noEndDateRadio -> {
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
                    this@AddReminderFragment.viewModel.durationModel,
                    childFragmentManager
                )
            }
            setDurationDaysBtn.setOnClickListener {
                ReminderDialogFragmentsDisplayer.showDurationDaysPickerDialog(
                    this@AddReminderFragment.viewModel.durationModel,
                    childFragmentManager
                )
            }
            setEndDateBtn.setOnClickListener {
                ReminderDialogFragmentsDisplayer.showEndDatePickerDialog(
                    this@AddReminderFragment.viewModel.durationModel,
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
            viewModel = this@AddReminderFragment.viewModel
            confirmButton.setOnClickListener { addTaskWithReminder() }

            setTimeOfNotification.setOnClickListener {
                ReminderDialogFragmentsDisplayer.showNotificationPickerDialog(
                    this@AddReminderFragment.viewModel.notificationModel,
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

            /**
             * when _addedTask is not null then  notification alarm should be set
             */
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
        viewModel.saveTask()
        findNavController().navigate(
            AddReminderFragmentDirections.navigationAddReminderToNavigationMyTasks()
        )
    }


}


interface DialogFragmentsDisplayer


object ReminderDialogFragmentsDisplayer :
    DialogFragmentsDisplayer {


    fun showNotificationPickerDialog(
        defaultNotificationModel: NotificationModel
        , childFragmentManager: FragmentManager
    ) {
        NotificationTimePickerFragment(
            defaultNotificationModel
        ).show(
            childFragmentManager,
            "Notification dialog tag"
        )

    }


    fun showDurationDaysPickerDialog(
        defaultDurationModel: DurationModel
        , childFragmentManager: FragmentManager

    ) {
        DaysDurationPickerFragment(
            defaultDurationModel
        ).show(childFragmentManager, "days duration dialog")
    }


    fun showDaysOfWeekPickerDialog(
        frequencyModel: FrequencyModel,
        childFragmentManager: FragmentManager

    ) {
        WeekDayPickerFragment(frequencyModel)
            .show(childFragmentManager, "weekday picker dialog")
    }


    fun showFrequencyPickerDialog(
        frequencyModel: FrequencyModel
        , childFragmentManager: FragmentManager

    ) {
        FrequencyPickerFragment(frequencyModel)
            .show(childFragmentManager, "FREQUENCY PICKER DIALOG")
    }


    fun showEndDatePickerDialog(
        defaultDurationModel: DurationModel
        , childFragmentManager: FragmentManager

    ) {
        EndDatePickerFragment(defaultDurationModel)
            .show(
                childFragmentManager,
                AddReminderFragment.END_DATE_DIALOG_TAG
            )
    }

    fun showBegDatePickerDialog(
        defaultDurationModel: DurationModel
        , childFragmentManager: FragmentManager

    ) {
        BeginningDatePickerFragment(
            defaultDurationModel
        ).show(
            childFragmentManager,
            AddReminderFragment.BEGINNING_DATE_DIALOG_TAG
        )
    }
}
