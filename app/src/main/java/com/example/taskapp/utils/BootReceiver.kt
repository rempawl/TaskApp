package com.example.taskapp.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(p0: Context?, intent: Intent?) {
        if (intent?.action == "android.intent.action.BOOT_COMPLETED") {

        }
    }
}



