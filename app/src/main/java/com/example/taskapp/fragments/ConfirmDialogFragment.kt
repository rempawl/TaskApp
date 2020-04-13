package com.example.taskapp.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.taskapp.R


class ConfirmDialogFragment(
    private val message: String
) : DialogFragment() {


    lateinit var listener: OnConfirmSelectedListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val frag = parentFragment ?: throw IllegalStateException("no parent fragment")
        when (frag) {
            is OnConfirmSelectedListener -> listener = frag
            else -> throw NotImplementedError("Parent fragment should implement OnConfirmSelectedListener interface")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        retainInstance = true

        return activity?.let {
            AlertDialog
                .Builder(requireContext())
                .setTitle(message)
                .setPositiveButton(R.string.confirm) { _, _ -> listener.onConfirmSelected()
//                    dismiss()
                }
                .setNegativeButton(R.string.cancel) { _, _ -> }
                .create()

        } ?: throw IllegalStateException()
    }


    fun setOnConfirmSelectedListener(listener: OnConfirmSelectedListener) {
        this.listener = listener
    }

    interface OnConfirmSelectedListener {
        fun onConfirmSelected()

    }


    companion object {
        const val TAG = "delete dialog fragment tag"

    }
}