package com.example.taskapp.workers

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UpdateRemindersWorkerInitializer @Inject constructor() :
    WorkersInitializer {


    override fun createConstraints(): Constraints {
        return Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

    }

    override fun createWorkRequest(constraints: Constraints): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<UpdateRemindersWorker>(
            1,
            TimeUnit.DAYS
        )
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .setConstraints(constraints)
            .build()
    }

    override fun enqueueWorkRequest(workRequest: WorkRequest, context: Context) {
        check(workRequest is PeriodicWorkRequest) { " Work request is of the wrong type ${workRequest.javaClass}" }

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                UpdateRemindersWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
    }

    companion object

}