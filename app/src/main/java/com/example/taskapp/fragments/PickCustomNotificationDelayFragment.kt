package com.example.taskapp.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.NumberPicker
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.fragment.findNavController
import com.example.taskapp.MainActivity
import com.example.taskapp.MyApp.Companion.TASK_KEY
import com.example.taskapp.database.entities.task.TaskMinimal
import com.example.taskapp.databinding.PickCustomNotificationDelayFragmentBinding
import com.example.taskapp.di.viewModel
import com.example.taskapp.utils.EventObserver
import com.example.taskapp.utils.notification.NotificationIntentFactory
import com.example.taskapp.utils.notification.NotificationManagerHelper
import com.example.taskapp.viewmodels.PickCustomNotificationDelayViewModel
import javax.inject.Inject


class PickCustomNotificationDelayFragment : Fragment() {

    private val appComponent by lazy {
        (activity as MainActivity).appComponent
    }

    private val viewModel: PickCustomNotificationDelayViewModel by viewModel {
        injectViewModel()
    }

    @Inject
    lateinit var notificationIntentFactory: NotificationIntentFactory

    @Inject
    lateinit var notificationManagerHelper: NotificationManagerHelper

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectMembers()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = PickCustomNotificationDelayFragmentBinding
            .inflate(inflater, container, false) ?: throw IllegalStateException("binding is null")
        setUpBinding(binding)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUpObservers()
        notificationManagerHelper.cancelTaskNotification()
    }

    private fun setUpObservers() {
        viewModel.broadcastNotHandled.observe(viewLifecycleOwner, EventObserver {
            sendDelayNotificationBroadcast(requireContext())
        })
    }


    private fun setUpBinding(binding: PickCustomNotificationDelayFragmentBinding) {
        binding.apply {
            delayPicker.apply(setUpDelayPicker())
            confirmButton.setOnClickListener {
                onConfirmClick()
            }
        }
    }

    private fun setUpDelayPicker(): (NumberPicker).() -> Unit {
        return {
            minValue = MIN_VALUE
            maxValue = MAX_VALUE
            value = DEFAULT_VALUE
            setOnValueChangedListener { _, _, newVal -> viewModel.delayValue = newVal }
        }
    }

    private fun onConfirmClick() {
        context?.let { context ->
            sendDelayNotificationBroadcast(context)
        } ?: viewModel.onBroadcastNotHandled()
    }

    private fun sendDelayNotificationBroadcast(ctx: Context) {
        val task = arguments?.get(TASK_KEY) as TaskMinimal

        val intent = createIntent(task)

        LocalBroadcastManager.getInstance(ctx).sendBroadcast(intent)
        navigateToTodayFragment()
    }

    private fun createIntent(task: TaskMinimal): Intent {
        return notificationIntentFactory.createDelayNotificationIntent(viewModel.delayValue, task)
    }

    private fun navigateToTodayFragment() {
        findNavController().navigate(
            PickCustomNotificationDelayFragmentDirections
                .navigationPickCustomDelayToNavigationToday()
        )
    }

    private fun injectMembers() {
        appComponent.inject(this)
    }

    private fun injectViewModel(): PickCustomNotificationDelayViewModel {
        return appComponent.pickCustomNotificationDelayViewModel
    }

    companion object {
        const val MAX_VALUE = 60
        const val MIN_VALUE = 5
        const val DEFAULT_VALUE = 30
        const val DELAY_VALUE_KEY = "delay value"

        fun newInstance() = PickCustomNotificationDelayFragment()
    }

//todo onNavigateUp -> send broadcast with current delay time

}
