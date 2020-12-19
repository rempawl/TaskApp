package com.example.taskapp.utils.bindingArranger

import androidx.fragment.app.Fragment
import com.example.taskapp.databinding.AddEditTaskFragmentBinding
import com.example.taskapp.viewmodels.reminder.ReminderDurationState
import com.example.taskapp.viewmodels.reminder.ReminderFrequencyState
import com.example.taskapp.viewmodels.reminder.ReminderViewModel

class EditTaskBindingArranger(
    binding: AddEditTaskFragmentBinding, fragment: Fragment, viewModel: ReminderViewModel
) : TaskBindingArranger(binding, fragment, viewModel) {


    init {
        setUp()
    }

    override fun setUp() {
        super.setUpBinding()

        binding.apply {

            durationRadioGroup.run {
                when (this@EditTaskBindingArranger.viewModel.durationModel.durationState) {
                    is ReminderDurationState.NoEndDate -> check(noEndDateRadio.id)
                    is ReminderDurationState.DaysDuration -> check(xDaysDurationRadio.id)
                    is ReminderDurationState.EndDate -> check(endDateRadio.id)
                }
            }
            frequencyRadioGroup.run {
                when (this@EditTaskBindingArranger.viewModel.frequencyModel.frequencyState) {
                    is ReminderFrequencyState.Daily -> check(dailyFreqRadio.id)
                    is ReminderFrequencyState.WeekDays -> check(daysOfWeekRadio.id)
                }
            }
        }

    }

}
