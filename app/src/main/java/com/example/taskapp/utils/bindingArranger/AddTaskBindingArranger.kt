package com.example.taskapp.utils.bindingArranger

import android.view.View
import androidx.fragment.app.Fragment
import com.example.taskapp.databinding.AddEditTaskFragmentBinding
import com.example.taskapp.viewmodels.AddTaskViewModel
import com.example.taskapp.viewmodels.reminder.ReminderViewModel

class AddTaskBindingArranger(
    binding: AddEditTaskFragmentBinding, fragment: Fragment, viewModel: ReminderViewModel,
    onConfirmClickListener: View.OnClickListener
) : AddEditTaskBindingArranger(binding, fragment, viewModel) {

    override val confirmButtonListener: View.OnClickListener = onConfirmClickListener

    init {
        setUp()
    }

    override fun setUp() {
        setUpBinding()
        require(viewModel is AddTaskViewModel) { " wrong type of viewModel" }
        val focusListener = viewModel.onFocusTaskName

        binding.apply {
            taskName.onFocusChangeListener = focusListener

        }

    }

}
