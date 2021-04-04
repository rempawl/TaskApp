package com.example.taskapp

import android.app.Application
import com.example.taskapp.di.AppComponent
import com.example.taskapp.di.DaggerAppComponent
import com.example.taskapp.utils.providers.DispatcherProvider
import com.example.taskapp.workers.WorkersInitializer
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject


//todo settings menu
//todo tests
//todo Realization Entity

open class MyApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory()
            .create(applicationContext)
    }

    @Inject
    lateinit var workersInitializer: WorkersInitializer

    @Inject
    lateinit var dispatcherProvider: DispatcherProvider

    private val applicationScope by lazy {
        CoroutineScope(dispatcherProvider.defaultDispatcher + Job())
    }

    override fun onCreate() {
        super.onCreate()
        injectMembers()
        delayedInit()
    }

    override fun onTerminate() {
        super.onTerminate()
        applicationScope.cancel()
    }

    protected open fun injectMembers() {
        appComponent.inject(this)
    }




    private fun delayedInit() {
        applicationScope.launch {
            AndroidThreeTen.init(this@MyApp)
            workersInitializer.setUpWorkers(context = applicationContext)
        }
    }

    companion object {
        const val PREFERENCES_NAME = "com.example.taskapp"


        const val TASK_KEY = "task"
        const val TASK_CHANNEL_ID = "pending task Notifications channel"
        const val TASK_NOTIFICATION_ID = 0x1
        const val CREATE_NOTIFICATION_ACTION = "create notification action"

    }

}