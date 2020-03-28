package com.example.taskapp.workers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import javax.inject.Inject


class BootReceiver : BroadcastReceiver() {

    //todo
    @Inject
    lateinit var  workersCreator: WorkersInitializer

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED" && context != null) {
//            (context as MyApp).appComponent.inject()
            workersCreator.setUpWorkers(context)
        }
    }

}



