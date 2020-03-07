package com.example.taskapp.workers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import javax.inject.Inject


class BootReceiver @Inject constructor(private val workersCreator: WorkersInitializer)  : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED" && context != null) {
            workersCreator.setUpWorkers(context)
        }
    }

}



