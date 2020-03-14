package com.example.taskapp.workers

import android.content.Context
import androidx.work.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface WorkersInitializer {
    fun setUpWorkers(context: Context)
}


class UpdateRemindersWorkerInitializer  @Inject constructor() : WorkersInitializer{

    override fun setUpWorkers(context: Context) {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val repeatingRequest =
            PeriodicWorkRequestBuilder<UpdateRemindersWorker>(1, TimeUnit.DAYS)
                .setBackoffCriteria(
                    BackoffPolicy.LINEAR,
                    PeriodicWorkRequest.MIN_BACKOFF_MILLIS,
                    TimeUnit.MILLISECONDS
                )
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(context)
            .enqueueUniquePeriodicWork(
                UpdateRemindersWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingRequest
            )
    }

    companion object

}
