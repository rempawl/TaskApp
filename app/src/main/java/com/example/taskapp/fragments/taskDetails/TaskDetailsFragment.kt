package com.example.taskapp.fragments.taskDetails

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.taskapp.MainActivity
import com.example.taskapp.MyApp.Companion.DATE_FORMATTER
import com.example.taskapp.R
import com.example.taskapp.data.reminder.NotificationTime
import com.example.taskapp.data.reminder.Reminder
import com.example.taskapp.databinding.TaskDetailsFragmentBinding
import com.example.taskapp.di.viewModel
import com.example.taskapp.fragments.ConfirmDialogFragment
import com.example.taskapp.utils.providers.DispatcherProvider
import com.example.taskapp.viewmodels.reminder.DayOfWeekValue
import com.example.taskapp.viewmodels.reminder.ReminderDurationState
import com.example.taskapp.viewmodels.reminder.ReminderFrequencyState
import com.example.taskapp.viewmodels.taskDetails.TaskDetailsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject


class TaskDetailsFragment : Fragment(), ConfirmDialogFragment.OnConfirmSelectedListener {

    companion object {
        fun newInstance() =
            TaskDetailsFragment()
    }

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    private val args: TaskDetailsFragmentArgs by navArgs()


    private val appComponent by lazy {
        (activity as MainActivity).appComponent
    }

    private val viewModel: TaskDetailsViewModel by viewModel {
        appComponent.taskDetailsViewModelFactory.create(args.taskID)
    }

    private var binding: TaskDetailsFragmentBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TaskDetailsFragmentBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        setUpObservers()
        setUpBinding()
        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.details_options, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.navigation_edit_task -> navigateToEditTask()
            R.id.delete_task -> showDeleteDialog()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setUpObservers() {
        viewModel.reminder.observe(viewLifecycleOwner, { reminder ->
            if (reminder != null) setupReminderLayout(reminder)
        })

    }

    private fun navigateToMyTasks() {
        findNavController().navigate(
            TaskDetailsFragmentDirections.navigationTaskDetailsToNavigationMyTasks()
        )

    }

    private fun setUpBinding() {
        binding!!.apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@TaskDetailsFragment.viewModel
        }
    }

    private fun navigateToEditTask() {
        //todo
        /*viewModel.result.observe(viewLifecycleOwner, { task ->
            findNavController().navigate(
                TaskDetailsFragmentDirections
                    .navigationTaskDetailsToNavigationEditTask(task)
            )
        })*/
    }

    private fun showDeleteDialog() {
        ConfirmDialogFragment(
            title = getString(R.string.task_delete_title),
            positiveText = getString(R.string.confirm),
            listener = this
        )
            .show(childFragmentManager, ConfirmDialogFragment.TAG)
    }

    private fun setupReminderLayout(reminder: Reminder) {
        val durationState = reminder.duration.convertToDurationState()
        val frequencyState = reminder.frequency.convertToFrequencyState()
        setDurationText(durationState)
        setFrequencyText(frequencyState)
        setNotificationText(reminder.notificationTime)

        binding!!.apply {
            reminderLayout.visibility = View.VISIBLE
            realizationDateText.text = getString(
                R.string.next_realization_date,
                reminder.realizationDate.format(DATE_FORMATTER)
            )
        }
    }

    private fun setNotificationText(notificationTime: NotificationTime) {
        binding!!.notificationText.text = if (notificationTime.isSet) {
            "${getString(R.string.notification_time)}: ${
                notificationTime.convertToLocalTime()
                    .format(DateTimeFormatter.ISO_LOCAL_TIME)
            }"
        } else {
            getString(R.string.no_notificaton)
        }
    }

    private fun setFrequencyText(frequencyState: ReminderFrequencyState) {
        var freqText = "${getString(R.string.frequency)}: "
        freqText += when (frequencyState) {
            is ReminderFrequencyState.Daily -> resources
                .getQuantityString(
                    R.plurals.daily_frequency, frequencyState.frequency, frequencyState.frequency
                )
            is ReminderFrequencyState.WeekDays -> getWeekDays(frequencyState.daysOfWeek)
        }
        binding!!.frequencyText.text = freqText
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
        binding!!.durationText.text = durText
    }


    override fun onConfirmSelected() {
        CoroutineScope(Dispatchers.IO).launch {
            viewModel.deleteTask()
        }
        navigateToMyTasks()
    }
}

