package com.example.taskapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.taskapp.R
import com.example.taskapp.viewmodels.EditTaskViewModel

class EditTaskFragment : Fragment() {

    companion object {
        fun newInstance() = EditTaskFragment()
    }

    private lateinit var viewModel: EditTaskViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.edit_task_fragment, container, false)
    }

}
