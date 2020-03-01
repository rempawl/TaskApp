package com.example.taskapp.fragments

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
import com.example.taskapp.databinding.AddReminderFragmentBinding
import com.example.taskapp.di.viewModel
import com.example.taskapp.fragments.reminder.*
import com.example.taskapp.utils.VisibilityChanger.changeViewsHelper
import com.example.taskapp.viewmodels.AddReminderViewModel
import com.google.android.material.radiobutton.MaterialRadioButton
import org.threeten.bp.format.DateTimeFormatter


class AddReminderFragment : Fragment(),
    Reminder {

    companion object {
        fun newInstance() = AddReminderFragment()
        const val END_DATE_DIALOG_TAG: String = "END DATE DIALOG"
        const val BEGINNING_DATE_DIALOG_TAG = "Beginning date Dialog"
        val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
    }


    private val viewModel: AddReminderViewModel by viewModel {
        (activity as MainActivity).appComponent.addReminderViewModelFactory
            .create(args.taskDetails)
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

        viewModel.getToastText().observe(viewLifecycleOwner, Observer { id ->
            if (id != null) {
                Toast.makeText(context, getString(id), Toast.LENGTH_SHORT).show()
            }
        })

        return binding?.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpBinding()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.apply {
            viewModel = null
            lifecycleOwner = null
        }
        binding = null
    }

     override  fun setUpBinding() {
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@AddReminderFragment.viewModel
            setTimeOfNotification.setOnClickListener { showNotificationPickerDialog() }
            confirmButton.setOnClickListener { addTaskWithReminder() }
        }
        setUpDurationLayout()
        setupFrequencyLayout()
    }

     override fun showNotificationPickerDialog() {
        NotificationTimePickerFragment(
            viewModel.notificationModel
        ).show(
            childFragmentManager,
            "Notification dialog tag"
        )

    }


    override fun setUpDurationLayout() {
        binding?.apply {
            beginningDateBtn.setOnClickListener { showBegDatePickerDialog() }
            setDurationDaysBtn.setOnClickListener { showDurationDaysPickerDialog() }
            setEndDateBtn.setOnClickListener { showEndDatePickerDialog() }
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
            setDailyFrequencyBtn.setOnClickListener { showFrequencyPickerDialog() }
            setDaysOfWeekBtn.setOnClickListener { showDaysOfWeekPickerDialog() }
        }
    }


    override fun onFrequencyRadioCheck(id: Int) {
        val frequencyModel = viewModel.frequencyModel
        when (activity?.findViewById<MaterialRadioButton>(id)) {
            binding?.dailyFreqRadio -> {
                frequencyModel.setDailyFrequency()
            }
            binding?.daysOfWeekRadio -> {
                frequencyModel.setDaysOfWeekFrequency()
            }

            else -> throw NoSuchElementException("There is no matching button")
        }

        setFrequencyButtonsVisibility(id)

    }

    private fun addTaskWithReminder() {
        viewModel.saveTaskWithReminder()
        findNavController().navigate(
            AddReminderFragmentDirections.navigationAddReminderToNavigationMyTasks()
        )
    }


    override fun showDurationDaysPickerDialog() {
        DaysDurationPickerFragment(
            viewModel.durationModel
        ).show(childFragmentManager, "days duration dialog")
    }


    override fun showDaysOfWeekPickerDialog() {
        WeekDayPickerFragment(viewModel.frequencyModel)
            .show(childFragmentManager, "weekday picker dialog")
    }


    override fun showFrequencyPickerDialog() {
        FrequencyPickerFragment(viewModel.frequencyModel)
            .show(childFragmentManager, "FREQUENCY PICKER DIALOG")
    }


    override fun showEndDatePickerDialog() {
        EndDatePickerFragment(viewModel.durationModel)
            .show(childFragmentManager,
                END_DATE_DIALOG_TAG
            )
    }

    override fun showBegDatePickerDialog() {
        BeginningDatePickerFragment(
            viewModel.durationModel
        ).show(childFragmentManager,
            BEGINNING_DATE_DIALOG_TAG
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
            binding?.xDaysDurationRadio -> changeViewsHelper(
                listOf(binding!!.setDurationDaysBtn),
                allBtns
            )
            binding?.endDateRadio -> changeViewsHelper(listOf(binding!!.setEndDateBtn), allBtns)
            binding?.noEndDateRadio -> changeViewsHelper(null, allBtns)
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
            binding?.daysOfWeekRadio -> changeViewsHelper(
                listOf(binding!!.setDaysOfWeekBtn),
                allBtns
            )
            else -> throw NoSuchElementException("There is no matching button")
        }

    }


}


