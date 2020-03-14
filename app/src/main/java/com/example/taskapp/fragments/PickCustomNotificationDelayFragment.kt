package com.example.taskapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.taskapp.MainActivity
import com.example.taskapp.database.entities.TaskMinimal
import com.example.taskapp.databinding.PickCustomNotificationDelayFragmentBinding
import com.example.taskapp.utils.NotificationIntentFactory
import com.example.taskapp.viewmodels.PickCustomNotificationDelayViewModel
import com.example.taskapp.workers.notification.CreateNotificationBroadcastReceiver.Companion.TASK_KEY
import javax.inject.Inject


class PickCustomNotificationDelayFragment : Fragment() {

    @Inject
    lateinit var viewModel: PickCustomNotificationDelayViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).appComponent.inject(this)
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

    private fun setUpBinding(binding: PickCustomNotificationDelayFragmentBinding) {
        binding.apply {
            delayPicker.apply {
                minValue = MIN_VALUE
                maxValue = MAX_VALUE
                value = DEFAULT_VALUE
                setOnValueChangedListener { _, _, newVal ->
                    viewModel.delayValue = newVal
                }
            }
            confirmButton.setOnClickListener { sendDelayNotificationBroadcast() }
        }
    }

    private fun sendDelayNotificationBroadcast() {
        val task = arguments?.get(TASK_KEY) as TaskMinimal
        context?.let { ctx ->
            val intent = NotificationIntentFactory.createDelayNotificationIntent(
                ctx,
                viewModel.delayValue,
                task
            )
            LocalBroadcastManager.getInstance(ctx).sendBroadcast(intent)


        }
    }

    companion object {
        const val MAX_VALUE = 60
        const val MIN_VALUE = 5
        const val DEFAULT_VALUE = 30
        const val DELAY_VALUE_KEY = "delay value"

        fun newInstance() = PickCustomNotificationDelayFragment()
    }

//todo on navigate up send broadcast with current time

}
