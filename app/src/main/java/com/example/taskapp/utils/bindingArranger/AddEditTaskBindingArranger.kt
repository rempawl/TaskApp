package com.example.taskapp.utils.bindingArranger

import android.view.View
import androidx.fragment.app.Fragment
import com.example.taskapp.databinding.AddEditTaskFragmentBinding
import com.example.taskapp.utils.VisibilityChanger
import com.example.taskapp.viewmodels.reminder.ReminderViewModel
import com.example.taskapp.utils.ReminderDialogFragmentsDisplayer as DialogDisplayer

abstract class AddEditTaskBindingArranger constructor(
    protected val binding: AddEditTaskFragmentBinding,
    private val fragment: Fragment,
    protected val viewModel: ReminderViewModel
) {

    protected abstract val confirmButtonListener: View.OnClickListener

    protected abstract fun setUp()

    protected fun setUpBinding() {
        binding.apply {
            viewModel = this@AddEditTaskBindingArranger.viewModel
            lifecycleOwner = fragment.viewLifecycleOwner

            confirmButton.setOnClickListener(confirmButtonListener)

            setTimeOfNotification.setOnClickListener {
                DialogDisplayer.showNotificationPickerDialog(
                    this@AddEditTaskBindingArranger.viewModel.notificationModel,
                    fragment.childFragmentManager
                )
            }
        }
        setUpDurationLayout()
        setupFrequencyLayout()

    }

    private fun setFrequencyButtonsVisibility(id: Int) {
        binding.run {
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

    private fun setUpDurationLayout() {
        binding.apply {
            beginningDateBtn.setOnClickListener {
                DialogDisplayer.showBegDatePickerDialog(
                    this@AddEditTaskBindingArranger.viewModel.durationModel,
                    fragment.childFragmentManager
                )
            }
            setDurationDaysBtn.setOnClickListener {
                DialogDisplayer.showDurationDaysPickerDialog(
                    this@AddEditTaskBindingArranger.viewModel.durationModel,
                    fragment.childFragmentManager
                )
            }
            setEndDateBtn.setOnClickListener {
                DialogDisplayer.showEndDatePickerDialog(
                    this@AddEditTaskBindingArranger.viewModel.durationModel,
                    fragment.childFragmentManager
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

    private fun setDurationButtonsVisibility(id: Int) {
        binding.run {
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
        binding.run {
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

    private fun setupFrequencyLayout() {
        binding.apply {
            frequencyRadioGroup.apply {
                setFrequencyButtonsVisibility(checkedRadioButtonId) //on rotation
                setOnCheckedChangeListener { _, id ->
                    onFrequencyRadioCheck(id)
                    setFrequencyButtonsVisibility(id)
                }
            }
            setDailyFrequencyBtn.setOnClickListener {
                DialogDisplayer.showFrequencyPickerDialog(
                    this@AddEditTaskBindingArranger.viewModel.frequencyModel,
                    fragment.childFragmentManager
                )
            }
            setDaysOfWeekBtn.setOnClickListener {
                DialogDisplayer.showDaysOfWeekPickerDialog(
                    childFragmentManager = fragment.childFragmentManager,
                    frequencyModel = this@AddEditTaskBindingArranger.viewModel.frequencyModel
                )
            }
        }
    }

    private fun onFrequencyRadioCheck(id: Int) {
        val frequencyModel = this@AddEditTaskBindingArranger.viewModel.frequencyModel
        binding.run {
            when (id) {
                dailyFreqRadio.id -> {
                    frequencyModel.setDailyFrequency()
                }
                daysOfWeekRadio.id -> {
                    frequencyModel.setDaysOfWeekFrequency(null)
                }
                else -> throw NoSuchElementException("There is no matching button")
            }
        }
        setFrequencyButtonsVisibility(id)

    }


}
