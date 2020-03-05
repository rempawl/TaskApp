package com.example.taskapp

import android.app.Application
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.taskapp.di.AppComponent
import com.example.taskapp.di.DaggerAppComponent
import com.example.taskapp.work.UpdateNotificationDatesWorker
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

//todo changing updateDate
//todo mockK
//todo editTask init reminder state radio check
//todo showing notifications of upcoming tasks
//todo settings menu
//todo tests
//todo myTasks switchMap
class MyApp : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory()
            .create(applicationContext)
    }
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this@MyApp)

//        delayedInit()
    }


    private fun delayedInit() {
        applicationScope.launch {

        }
    }

    private fun setupWorkers() {
        val constraints = Constraints.Builder()
            .setRequiresBatteryNotLow(true)
            .build()

        val repeatingRequest =
            PeriodicWorkRequestBuilder<UpdateNotificationDatesWorker>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance(applicationContext)
            .enqueueUniquePeriodicWork(
                UpdateNotificationDatesWorker.WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingRequest
            )

    }


}