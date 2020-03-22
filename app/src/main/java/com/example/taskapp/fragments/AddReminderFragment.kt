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
import com.example.taskapp.database.entities.Task
import com.example.taskapp.databinding.AddReminderFragmentBinding
import com.example.taskapp.di.viewModel
import com.example.taskapp.fragments.ReminderDialogFragmentsDisplayer.showBegDatePickerDialog
import com.example.taskapp.fragments.ReminderDialogFragmentsDisplayer.showDaysOfWeekPickerDialog
import com.example.taskapp.fragments.ReminderDialogFragmentsDisplayer.showDurationDaysPickerDialog
import com.example.taskapp.fragments.ReminderDialogFragmentsDisplayer.showEndDatePickerDialog
import com.example.taskapp.fragments.ReminderDialogFragmentsDisplayer.showFrequencyPickerDialog
import com.example.taskapp.fragments.ReminderDialogFragmentsDisplayer.showNotificationPickerDialog
import com.example.taskapp.fragments.reminder.*
import com.example.taskapp.utils.VisibilityChanger.changeViewsHelper
import com.example.taskapp.viewmodels.reminder.durationModel.DurationModel
import com.example.taskapp.viewmodels.reminder.frequencyModel.FrequencyModel
import com.example.taskapp.viewmodels.reminder.notificationModel.NotificationModel
import com.example.taskapp.workers.AlarmCreator
import com.google.android.material.radiobutton.MaterialRadioButton

class AddReminderFragment : Fragment(), Reminder {

    companion object {
        fun newInstance() = AddReminderFragment()
        const val END_DATE_DIALOG_TAG: String = "END DATE DIALOG"
        const val BEGINNING_DATE_DIALOG_TAG = "Beginning date Dialog"
    }


    private val viewModel by viewModel {
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

    }

    override fun onDestroyView() {
        super.onDestroyView()

        binding?.apply {
            viewModel = null
            lifecycleOwner = null
        }
        binding = null
    }

    override fun setUpBinding() {
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@AddReminderFragment.viewModel
            setTimeOfNotification.setOnClickListener {
                showNotificationPickerDialog(
                    this@AddReminderFragment.viewModel.notificationModel,
                    childFragmentManager
                )
            }
            confirmButton.setOnClickListener { addTaskWithReminder() }
        }
        setUpDurationLayout()
        setupFrequencyLayout()
    }


    override fun setUpDurationLayout() {
        binding?.apply {
            beginningDateBtn.setOnClickListener {
                showBegDatePickerDialog(
                    this@AddReminderFragment.viewModel.durationModel,
                    childFragmentManager
                )
            }
            setDurationDaysBtn.setOnClickListener {
                showDurationDaysPickerDialog(
                    this@AddReminderFragment.viewModel.durationModel,
                    childFragmentManager
                )
            }
            setEndDateBtn.setOnClickListener {
                showEndDatePickerDialog(
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

    override fun onDurationRadioChecked(id: Int) {
        val binding = binding ?: return
        val durationModel = viewModel.durationModel
        when (activity?.findViewById<View>(id)!!) {
            binding.xDaysDurationRadio -> {
                durationModel.setDaysDurationState()
            }
            binding.endDateRadio -> {
                durationModel.setEndDateDurationState()
            }
            binding.noEndDateRadio -> {
                durationModel.setNoEndDateDurationState()
            }
            else -> throw NoSuchElementException("There is no matching button")
        }
        setDurationButtonsVisibility(id)
    }


    override fun setupFrequencyLayout() {
        binding?.apply {
            frequencyRadioGroup.apply {
                setFrequencyButtonsVisibility(checkedRadioButtonId) //on rotation
                setOnCheckedChangeListener { _, id ->
                    onFrequencyRadioCheck(id)
                    setFrequencyButtonsVisibility(id)
                }
            }
            setDailyFrequencyBtn.setOnClickListener {
                showFrequencyPickerDialog(
                    this@AddReminderFragment.viewModel.frequencyModel,
                    childFragmentManager
                )
            }
            setDaysOfWeekBtn.setOnClickListener {
                showDaysOfWeekPickerDialog(
                    this@AddReminderFragment.viewModel.frequencyModel,
                    childFragmentManager
                )
            }
        }
    }


    override fun onFrequencyRadioCheck(id: Int) {
        val frequencyModel = viewModel.frequencyModel
        when (activity?.findViewById<MaterialRadioButton>(id)) {
            binding!!.dailyFreqRadio -> {
                frequencyModel.setDailyFrequency()
            }
            binding!!.daysOfWeekRadio -> {
                frequencyModel.setDaysOfWeekFrequency()
            }

            else -> throw NoSuchElementException("There is no matching button")
        }

        setFrequencyButtonsVisibility(id)

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

    private fun setAlarm(task: Task) {
        context?.let { ctx ->
            AlarmCreator.setTaskNotificationAlarm(task, true, ctx)
        }
    }


    private fun addTaskWithReminder() {
        viewModel.saveTaskWithReminder()
        findNavController().navigate(
            AddReminderFragmentDirections.navigationAddReminderToNavigationMyTasks()
        )
    }


    /**
     * function responsible for changing visibility of buttons under RadioGroup depending
     * on current radio  checked
     */
    override fun setDurationButtonsVisibility(id: Int) {
        val allBtns = listOf(
            binding!!.setDurationDaysBtn,
            binding!!.setEndDateBtn
        )
        when (activity?.findViewById<View>(id)!!) {
            binding!!.xDaysDurationRadio -> changeViewsHelper(
                listOf(binding!!.setDurationDaysBtn),
                allBtns
            )
            binding!!.endDateRadio -> changeViewsHelper(listOf(binding!!.setEndDateBtn), allBtns)
            binding!!.noEndDateRadio -> changeViewsHelper(null, allBtns)
            else -> throw NoSuchElementException("There is no matching button")
        }
    }

    /**
     * function responsible for changing visibility of buttons under RadioGroup depending
     * on current radio  checked
     */
    override fun setFrequencyButtonsVisibility(id: Int) {
        val allBtns = listOf(
            binding!!.setDailyFrequencyBtn,
            binding!!.setDaysOfWeekBtn
        )
        when (activity?.findViewById<MaterialRadioButton>(id)) {
            binding!!.dailyFreqRadio -> changeViewsHelper(
                listOf(binding!!.setDailyFrequencyBtn),
                allBtns
            )
            binding!!.daysOfWeekRadio -> changeViewsHelper(
                listOf(binding!!.setDaysOfWeekBtn),
                allBtns
            )
            else -> throw NoSuchElementException("There is no matching button")
        }

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
