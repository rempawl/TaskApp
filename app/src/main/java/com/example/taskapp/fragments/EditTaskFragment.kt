package com.example.taskapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.taskapp.MainActivity
import com.example.taskapp.databinding.EditTaskFragmentBinding
import com.example.taskapp.di.viewModel
import com.example.taskapp.fragments.reminder.*
import com.example.taskapp.utils.VisibilityChanger
import com.example.taskapp.viewmodels.EditTaskViewModel
import com.google.android.material.radiobutton.MaterialRadioButton

class EditTaskFragment : Fragment(),
    Reminder {

    companion object {
        fun newInstance() = EditTaskFragment()
    }

    private val viewModel: EditTaskViewModel by viewModel {
        (activity as MainActivity)
            .appComponent.editTaskViewModelFactory.create(args.task)
    }

    private val args: EditTaskFragmentArgs by navArgs()

    private var binding: EditTaskFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = EditTaskFragmentBinding.inflate(inflater, container, false)
        viewModel.getToastText().observe(viewLifecycleOwner, Observer  { id ->
            if (id != null) {
                Toast.makeText(context, getString(id), Toast.LENGTH_SHORT).show()
            }
        })
        return binding!!.root
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

    override fun setUpBinding() {
        binding?.apply {
            viewModel = this@EditTaskFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
            confirmButton.setOnClickListener { editTask() }
            setTimeOfNotification.setOnClickListener { showNotificationPickerDialog() }
        }
        setUpDurationLayout()
        setupFrequencyLayout()
    }

    private fun editTask() {
        viewModel.saveEditedTask()
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
    }

    override fun showDurationDaysPickerDialog() {
        DaysDurationPickerFragment(viewModel.durationModel).show(childFragmentManager, "days duration dialog")
    }

    override fun showDaysOfWeekPickerDialog() {
        WeekDayPickerFragment(viewModel.frequencyModel)
            .show(childFragmentManager, "weekday picker dialog")


    }

    override fun showFrequencyPickerDialog() {
        FrequencyPickerFragment(this.viewModel.frequencyModel)
            .show(childFragmentManager, "FREQUENCY PICKER DIALOG")
    }

    override fun showEndDatePickerDialog() {
        EndDatePickerFragment(this.viewModel.durationModel)
            .show(childFragmentManager,
                AddReminderFragment.END_DATE_DIALOG_TAG
            )

    }

    override fun showBegDatePickerDialog() {
        BeginningDatePickerFragment(this.viewModel.durationModel).show(childFragmentManager,
            AddReminderFragment.BEGINNING_DATE_DIALOG_TAG
        )
    }

    override fun showNotificationPickerDialog() {
            NotificationTimePickerFragment(viewModel.notificationModel).show(childFragmentManager,"sth")
    }

    override fun setDurationButtonsVisibility(id: Int) {
        val allBtns = listOf(
            binding!!.setDurationDaysBtn,
            binding!!.setEndDateBtn
        )
        when (activity?.findViewById<View>(id)!!) {
            binding?.xDaysDurationRadio -> VisibilityChanger.changeViewsHelper(
                listOf(binding!!.setDurationDaysBtn),
                allBtns
            )
            binding?.endDateRadio -> VisibilityChanger.changeViewsHelper(
                listOf(binding!!.setEndDateBtn),
                allBtns
            )
            binding?.noEndDateRadio -> VisibilityChanger.changeViewsHelper(null, allBtns)
            else -> throw NoSuchElementException("There is no matching button")
        }

    }

    override fun setFrequencyButtonsVisibility(id: Int) {
        val allBtns = listOf(
            binding!!.setDailyFrequencyBtn,
            binding!!.setDaysOfWeekBtn
        )
        when (activity?.findViewById<MaterialRadioButton>(id)) {
            binding!!.dailyFreqRadio -> VisibilityChanger.changeViewsHelper(
                listOf(binding!!.setDailyFrequencyBtn),
                allBtns
            )
            binding?.daysOfWeekRadio -> VisibilityChanger.changeViewsHelper(
                listOf(binding!!.setDaysOfWeekBtn),
                allBtns
            )
            else -> throw NoSuchElementException("There is no matching button")
        }

    }

}
