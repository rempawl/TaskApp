package com.example.taskapp.utils.bindingArranger

import android.view.View
import androidx.fragment.app.Fragment
import com.example.taskapp.databinding.AddEditTaskFragmentBinding
import com.example.taskapp.viewmodels.reminder.ReminderViewModel

class EditTaskBindingArranger(
    binding: AddEditTaskFragmentBinding, fragment: Fragment, viewModel: ReminderViewModel,
    onClickListener: View.OnClickListener
) : AddEditTaskBindingArranger(binding, fragment, viewModel) {
    override val confirmButtonListener: View.OnClickListener = onClickListener

    init {
        setUp()
    }

    override fun setUp() {
        setUpBinding()
        binding.apply {
            taskName.isEnabled = false
            beginningDateBtn.isEnabled = false

            //todo isNotification Enabled
            //todo radioButton check on init
        }

    }

}
