package com.example.taskapp.utils

import android.content.ComponentName
import android.content.Context
import android.content.pm.PackageManager
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SetAlarmsWorker(context: Context, workerParameters: WorkerParameters) : CoroutineWorker(context,workerParameters) {
    override suspend fun doWork(): Result  = withContext(Dispatchers.IO){


        val receiver = ComponentName(applicationContext, BootReceiver::class.java)
        applicationContext.packageManager.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
        TODO()
    }


}