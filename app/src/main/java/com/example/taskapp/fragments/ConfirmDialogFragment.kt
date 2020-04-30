package com.example.taskapp.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.taskapp.R


class ConfirmDialogFragment(
    private val message: String,
    private val positiveText: String,
    val listener: OnConfirmSelectedListener

) : DialogFragment() {




    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        retainInstance = true

        return activity?.let {
            AlertDialog
                .Builder(requireContext())
                .setTitle(message)
                .setPositiveButton(positiveText) { _, _ -> listener.onConfirmSelected() }
                .setNegativeButton(R.string.cancel) { _, _ -> }
                .create()

        } ?: throw IllegalStateException()
    }

    interface OnConfirmSelectedListener {
        fun onConfirmSelected()

    }


    companion object {
        const val TAG = "delete dialog fragment tag"

    }
}