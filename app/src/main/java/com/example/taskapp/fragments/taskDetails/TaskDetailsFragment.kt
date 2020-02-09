package com.example.taskapp.fragments.taskDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.taskapp.MainActivity
import com.example.taskapp.R
import com.example.taskapp.database.entities.Reminder
import com.example.taskapp.databinding.TaskDetailsFragmentBinding
import com.example.taskapp.di.viewModel
import com.example.taskapp.utils.Converters
import com.example.taskapp.viewmodels.TaskDetailsViewModel

//todo layout
//todo delete

class TaskDetailsFragment : Fragment() {

    companion object {
        fun newInstance() =
            TaskDetailsFragment()
    }

    private val args: TaskDetailsFragmentArgs by navArgs()
    private val viewModel: TaskDetailsViewModel by viewModel {
        (activity as MainActivity)
            .appComponent.taskDetailsViewModelFactory
            .create(args.task)
    }
    private lateinit var binding: TaskDetailsFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TaskDetailsFragmentBinding.inflate(inflater, container, false)
        viewModel.task.observe(viewLifecycleOwner, Observer { task ->
            if(task.reminder != null){
                setupReminderLayout(task.reminder)

            }
        })
        setupBinding()

        return binding.root
    }


    private fun setupBinding() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@TaskDetailsFragment.viewModel
            deleteBtn.setOnClickListener {  }
        }
    }

    private fun setupReminderLayout(reminder: Reminder) {
        val converter = Converters.getInstance()

        val duration = reminder.duration.duration
        binding.apply {
            reminderLayout.visibility = View.VISIBLE
            begDate.text = getString(R.string.beginning_date, reminder.begDate)

            when {
                reminder.duration.noDate -> {
                    durationText.text = getString(R.string.no_end_date)
                }
                reminder.duration.isDate -> {
                    durationText.text = getString(
                        R.string.end_date_format,
                        converter.longToLocalDate(duration)
                    )
                }
                else -> {
                    durationText.text = resources.getQuantityString(
                        R.plurals.days_duration,
                        duration.toInt(),
                        duration
                    )
                }
            }
        }
    }
}
