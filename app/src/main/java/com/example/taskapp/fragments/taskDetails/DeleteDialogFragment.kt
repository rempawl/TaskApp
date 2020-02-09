package com.example.taskapp.fragments.taskDetails

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import com.example.taskapp.R
import com.example.taskapp.viewmodels.TaskDetailsViewModel
import kotlin.IllegalStateException

class DeleteDialogFragment(private val viewModel: TaskDetailsViewModel): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let{
            AlertDialog
                .Builder(requireContext())
                .setTitle("")
                .setPositiveButton(R.string.confirm) { _, _ -> deleteTask()}
                .setNegativeButton(R.string.cancel) { _, _ -> }
                .create()

        } ?: throw IllegalStateException()
    }

    private fun deleteTask() {
        viewModel.deleteTask()

    }
}