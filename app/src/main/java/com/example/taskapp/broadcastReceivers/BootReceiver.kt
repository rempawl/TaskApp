package com.example.taskapp.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.taskapp.workers.UpdateRemindersWorkerInitializer
import com.example.taskapp.workers.WorkersInitializer


class BootReceiver : BroadcastReceiver() {


    //todo inject
    private val  workersInitializer: WorkersInitializer =
        UpdateRemindersWorkerInitializer()

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED" && context != null) {
            workersInitializer.setUpWorkers(context)
        }
    }

}



