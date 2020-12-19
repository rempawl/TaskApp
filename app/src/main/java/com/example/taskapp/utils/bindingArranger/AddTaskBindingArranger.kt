package com.example.taskapp.utils.bindingArranger

import android.view.View
import androidx.fragment.app.Fragment
import com.example.taskapp.databinding.AddEditTaskFragmentBinding
import com.example.taskapp.viewmodels.AddTaskViewModel
import com.example.taskapp.viewmodels.reminder.ReminderViewModel

class AddTaskBindingArranger(
    binding: AddEditTaskFragmentBinding, fragment: Fragment, viewModel: ReminderViewModel
) : TaskBindingArranger(binding, fragment, viewModel) {


    init {
        setUp()
    }

    override fun setUp() {
        setUpBinding()
        require(viewModel is AddTaskViewModel) { " wrong type of viewModel" }



    }

}
