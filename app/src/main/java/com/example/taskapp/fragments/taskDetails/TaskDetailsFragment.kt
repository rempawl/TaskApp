package com.example.taskapp.fragments.taskDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskapp.MainActivity
import com.example.taskapp.MyApp.Companion.DATE_FORMATTER
import com.example.taskapp.R
import com.example.taskapp.database.entities.NotificationTime
import com.example.taskapp.database.entities.Reminder
import com.example.taskapp.databinding.TaskDetailsFragmentBinding
import com.example.taskapp.di.viewModel
import com.example.taskapp.utils.reminder.DayOfWeekValue
import com.example.taskapp.utils.reminder.ReminderDurationState
import com.example.taskapp.utils.reminder.ReminderFrequencyState
import com.example.taskapp.viewmodels.TaskDetailsViewModel
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

//todo notifications workManager
//todo editTask

class TaskDetailsFragment : Fragment() {

    companion object {
        fun newInstance() =
            TaskDetailsFragment()
    }

    private val args: TaskDetailsFragmentArgs by navArgs()
    private val viewModel: TaskDetailsViewModel by viewModel {
        (activity as MainActivity)
            .appComponent.taskDetailsViewModelFactory
            .create(args.taskID)
    }
    private var binding: TaskDetailsFragmentBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TaskDetailsFragmentBinding.inflate(inflater, container, false)

        setUpObservers()
        setUpBinding()
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.apply {
            viewModel = null
            lifecycleOwner = null

        }
        binding = null

    }


    private fun setUpObservers() {
        viewModel.getTaskDeleted().observe(viewLifecycleOwner, Observer { isDeleted ->
            if (isDeleted) {
                navigateToMyTasks()
            }
        })
        viewModel.task.observe(viewLifecycleOwner, Observer { task ->
            if (task.reminder != null) {
                setupReminderLayout(task.reminder)
            }
        })

    }

    private fun navigateToMyTasks() {
        findNavController()
            .navigate(
                TaskDetailsFragmentDirections.navigationTaskDetailsToNavigationMyTasks()
            )

    }

    private fun setUpBinding() {
        binding?.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@TaskDetailsFragment.viewModel

            deleteBtn.setOnClickListener { showDeleteDialog() }
            editBtn.setOnClickListener { navigateToEditTask() }
        }
    }

    private fun navigateToEditTask() {
        viewModel.task.observe(viewLifecycleOwner, Observer { task ->
            findNavController().navigate(TaskDetailsFragmentDirections
                .navigationTaskDetailsToNavigationEditTask(task))
        })
    }

    private fun showDeleteDialog() {
        DeleteDialogFragment(viewModel).show(childFragmentManager, DeleteDialogFragment.TAG)
    }

    private fun setupReminderLayout(reminder: Reminder) {
        val durationState = reminder.duration.convertToDurationState()
        val frequencyState = reminder.frequency.convertToFrequencyState()
        setDurationText(durationState)
        setFrequencyText(frequencyState)
        setNotificationText(reminder.notificationTime)

        binding?.apply {
            reminderLayout.visibility = View.VISIBLE
            begDate.text = getString(R.string.beginning_date, reminder.begDate.format(DATE_FORMATTER))
            realizationDateText.text = getString(R.string.next_realization_date,reminder.realizationDate.format(DATE_FORMATTER))
        }
    }

    private fun setNotificationText(notificationTime: NotificationTime) {
        binding?.notificationText?.text = if (notificationTime.isSet) {
            val txt = "${getString(R.string.notification_time)}: " +
                    LocalTime.of(notificationTime.hour, notificationTime.minute).format(
                        DateTimeFormatter.ISO_LOCAL_TIME
                    )
            txt
        } else {
            getString(R.string.no_notificaton)
        }
    }

    private fun setFrequencyText(frequencyState: ReminderFrequencyState) {
        var freqText = "${getString(R.string.frequency)} "
        freqText += when (frequencyState) {
            is ReminderFrequencyState.Daily -> resources
                .getQuantityString(
                    R.plurals.daily_frequency, frequencyState.frequency
                    , frequencyState.frequency
                )
            is ReminderFrequencyState.WeekDays -> getWeekDays(frequencyState.daysOfWeek)
        }
        binding?.apply{
            frequencyText.text = freqText

        }

    }

    private fun getWeekDays(weekDays: Set<DayOfWeekValue>): String {
        val days = org.threeten.bp.DayOfWeek.values()
        var i = 0
        var result = ""
        resources.getStringArray(R.array.week_days_list).forEach { dayName ->
            if (weekDays.contains(days[i].value)) {
                result += "$dayName, "
            }
            i++
        }
        return result
    }

    private fun setDurationText(durationState: ReminderDurationState) {
        var durText = "${getString(R.string.duration)}: "
        durText += when (durationState) {
            is ReminderDurationState.NoEndDate -> getString(R.string.no_end_date)
            is ReminderDurationState.EndDate -> getString(
                R.string.end_date_format,
                durationState.date
            )
            is ReminderDurationState.DaysDuration -> {
                resources.getQuantityString(
                    R.plurals.days_duration,
                    durationState.days,
                    durationState.days
                )
            }
        }
        binding?.durationText?.text = durText
    }
}
