package com.example.taskapp.fragments

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.taskapp.database.entities.task.DefaultTask
import com.example.taskapp.databinding.AddEditTaskFragmentBinding
import com.example.taskapp.utils.ReminderDialogFragmentsDisplayer
import com.example.taskapp.utils.ReminderDialogFragmentsDisplayer.showDurationDaysPickerDialog
import com.example.taskapp.utils.ReminderDialogFragmentsDisplayer.showFrequencyPickerDialog
import com.example.taskapp.utils.VisibilityChanger
import com.example.taskapp.utils.alarmCreator.AlarmCreator
import com.example.taskapp.viewmodels.reminder.ReminderViewModel
import javax.inject.Inject

abstract class AddEditTaskFragment : Fragment() {


    @Inject
    lateinit var alarmCreator: AlarmCreator

    protected abstract var binding: AddEditTaskFragmentBinding?

    protected abstract val viewModel: ReminderViewModel

    abstract fun navigateToMyTasks()


    private fun setAlarm(addedTask: DefaultTask) {
        alarmCreator.setTaskNotificationAlarm(addedTask, isToday = true)
    }

    private fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

    protected open fun setUpBinding() {
        binding?.apply {
            viewModel = this@AddEditTaskFragment.viewModel
            lifecycleOwner = viewLifecycleOwner
        } ?: throw IllegalStateException(BINDING_NULL)
    }


    open fun setupObservers(viewModel: ReminderViewModel) {
        viewModel.apply {
            isSetNotifBtnClicked.observe(viewLifecycleOwner) { isClicked ->
                if (isClicked) showSetNotifDialog(
                    viewModel
                )
            }
            toastText.observe(viewLifecycleOwner, { id ->
                if (id != null) showToast(getString(id))

            })

            isConfirmBtnClicked.observe(viewLifecycleOwner) { isCLicked -> if (isCLicked) navigateToMyTasks() }

            shouldSetAlarm.observe(viewLifecycleOwner, { (shouldSet, task) ->
                if (shouldSet && task != null) setAlarm(task)
            })

            frequencyRadioState.observe(viewLifecycleOwner) { state ->
                setFrequencyButtonsVisibility(state)
            }
            durationRadioState.observe(viewLifecycleOwner) { state ->
                setDurationButtonsVisibility(state)
            }

            isBeginningDateBtnClicked.observe(viewLifecycleOwner) { isClicked ->
                if (isClicked) showBegDateDialog(viewModel)
            }

            isEndDateBtnClicked.observe(viewLifecycleOwner) { isClicked ->
                if (isClicked) showEndDateDialog(
                    viewModel
                )
            }

            isDurationDaysBtnClicked.observe(viewLifecycleOwner) { isClicked ->
                if (isClicked) showDurationDaysPicker(viewModel)
            }

            isSetDailyFreqBtnClicked.observe(viewLifecycleOwner) { isClicked ->
                if (isClicked) showSetDailyFreqDialog(viewModel)
            }

            isSetDaysOfWeekBtnClicked.observe(viewLifecycleOwner) { isClicked ->
                if (isClicked) showSetDaysOfWeekDialog(viewModel)
            }
        }
    }

    private fun showDurationDaysPicker(
        viewModel: ReminderViewModel
    ) {
        showDurationDaysPickerDialog(
            viewModel.durationModel,
            childFragmentManager
        )
        viewModel.onDurationDaysPickerDialogShow()


    }

    private fun showEndDateDialog(viewModel: ReminderViewModel) {
        ReminderDialogFragmentsDisplayer.showEndDatePickerDialog(
            viewModel.durationModel,
            childFragmentManager
        )
        viewModel.onEndDateDialogShow()
    }


    private fun showBegDateDialog(viewModel: ReminderViewModel) {
        ReminderDialogFragmentsDisplayer.showBegDatePickerDialog(
            viewModel.durationModel,
            childFragmentManager
        )
        viewModel.onBegDateDialogShow()

    }


    private fun showSetNotifDialog(viewModel: ReminderViewModel) {
        ReminderDialogFragmentsDisplayer.showNotificationPickerDialog(
            viewModel.notificationModel,
            childFragmentManager
        )
        viewModel.onNotifDialogShow()

    }

    private fun showSetDailyFreqDialog(viewModel: ReminderViewModel) {
        showFrequencyPickerDialog(
            viewModel.frequencyModel,
            childFragmentManager
        )
        viewModel.onDailyFreqDialogShow()
    }

    private fun showSetDaysOfWeekDialog(viewModel: ReminderViewModel) {
        ReminderDialogFragmentsDisplayer.showDaysOfWeekPickerDialog(
            childFragmentManager = childFragmentManager,
            frequencyModel = viewModel.frequencyModel
        )
        viewModel.onDaysOfWeekDialogShow()
    }

    private fun setFrequencyButtonsVisibility(state: ReminderViewModel.FrequencyRadioState) {
        binding?.run {
            val allBtns = listOf(setDailyFrequencyBtn,setDaysOfWeekBtn)
            when (state) {
                is ReminderViewModel.FrequencyRadioState.DailyFreqRadioState -> {
                    VisibilityChanger.changeViewsHelper(listOf(setDailyFrequencyBtn), allBtns)
                    dailyFreqRadio.isChecked = true //for EditTask init
                }
                is ReminderViewModel.FrequencyRadioState.DaysOfWeekRadio -> {
                    VisibilityChanger.changeViewsHelper(listOf(setDaysOfWeekBtn), allBtns)
                    daysOfWeekRadio.isChecked = true
                }
            }
        } ?: throw IllegalStateException(BINDING_NULL)
    }

    private fun setDurationButtonsVisibility(state: ReminderViewModel.DurationRadioState) {
        binding?.run {
            val allBtns = listOf(
                setDurationDaysBtn,
                setEndDateBtn
            )
            when (state) {
                is ReminderViewModel.DurationRadioState.DaysDurationState -> VisibilityChanger.changeViewsHelper(
                    listOf(setDurationDaysBtn),
                    allBtns
                )
                is ReminderViewModel.DurationRadioState.EndDateDurationState -> VisibilityChanger.changeViewsHelper(
                    listOf(setEndDateBtn),
                    allBtns
                )
                is ReminderViewModel.DurationRadioState.NoEndDateDurationState -> VisibilityChanger.changeViewsHelper(
                    null,
                    allBtns
                )
            }
        } ?: throw IllegalStateException(BINDING_NULL)
    }

    companion object {
        const val BINDING_NULL = " binding is null"
    }
}