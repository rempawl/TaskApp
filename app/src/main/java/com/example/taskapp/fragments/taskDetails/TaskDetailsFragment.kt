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
import com.example.taskapp.R
import com.example.taskapp.database.entities.NotificationTime
import com.example.taskapp.database.entities.Reminder
import com.example.taskapp.databinding.TaskDetailsFragmentBinding
import com.example.taskapp.di.viewModel
import com.example.taskapp.fragments.addReminder.DayOfWeekValue
import com.example.taskapp.fragments.addReminder.ReminderDurationState
import com.example.taskapp.fragments.addReminder.ReminderFrequencyState
import com.example.taskapp.viewmodels.TaskDetailsViewModel
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter

//todo notifications workManager
//todo editTask
//todo expirationDate
//todo nextUpdateDate
//todo TodayTasks

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
    private lateinit var binding: TaskDetailsFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TaskDetailsFragmentBinding.inflate(inflater, container, false)

        setUpObservers()
        setupBinding()

        return binding.root
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
                TaskDetailsFragmentDirections.navigationTaskDetailsToNavigationHome()
                    .setShowMyTasks(true)
            )

    }


    private fun setupBinding() {
        binding.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@TaskDetailsFragment.viewModel
//            view = View.GONE
            deleteBtn.setOnClickListener { showDeleteDialog() }
            editBtn.setOnClickListener { navigateToEditTask() }
        }
    }

    private fun navigateToEditTask() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun showDeleteDialog() {
        DeleteDialogFragment(viewModel).show(childFragmentManager, "")
    }

    private fun setupReminderLayout(reminder: Reminder) {
        val durationState = reminder.duration.convertToDurationState()
        val frequencyState = reminder.frequency.convertToFrequencyState()
        setDurationText(durationState)
        setFrequencyText(frequencyState)
        setNotificationText(reminder.notificationTime)

        binding.apply {
            reminderLayout.visibility = View.VISIBLE
            begDate.text = getString(R.string.beginning_date, reminder.begDate)
        }
    }

    private fun setNotificationText(notificationTime: NotificationTime) {
        binding.notificationText.text = if (notificationTime.isSet) {
            val txt = "${getString(R.string.notification_time)}: " +
                    LocalTime.of(notificationTime.hour,notificationTime.minute).format(
                        DateTimeFormatter.ISO_LOCAL_TIME)
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
        binding.frequencyText.text = freqText
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
        binding.durationText.text = durText
    }
}
