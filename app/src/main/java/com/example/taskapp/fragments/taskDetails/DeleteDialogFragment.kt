package com.example.taskapp.fragments.taskDetails

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.taskapp.R
import com.example.taskapp.viewmodels.TaskDetailsViewModel

class DeleteDialogFragment(private val viewModel: TaskDetailsViewModel): DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        retainInstance = true
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
    companion object{
        const val TAG = "delete dialog fragment tag"

    }
}