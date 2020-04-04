package com.example.taskapp.workers

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


interface WorkersInitializer {
    fun setUpWorkers(context: Context) {
        val constraints = createConstraints()
        val workRequest = createWorkRequest(constraints)
        enqueueWorkRequest(context = context, workRequest = workRequest)
    }

     fun createConstraints(): Constraints
    fun createWorkRequest(constraints: Constraints): WorkRequest
    fun enqueueWorkRequest(workRequest: WorkRequest, context: Context)

}


class UpdateRemindersWorkerInitializer @Inject constructor() : WorkersInitializer {


    override fun createConstraints(): Constraints {
        return Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

    }

    override fun createWorkRequest(constraints: Constraints): PeriodicWorkRequest {
        return PeriodicWorkRequestBuilder<UpdateRemindersWorker>(1, TimeUnit.DAYS)
            .setBackoffCriteria(
                BackoffPolicy.LINEAR,
                PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .setConstraints(constraints)
            .build()
    }

    override fun enqueueWorkRequest(workRequest: WorkRequest, context: Context) {
        require(workRequest is PeriodicWorkRequest){ " Work request is of the wrong type "}
        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                UpdateRemindersWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                workRequest
            )
    }

    companion object

}
