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
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
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
        CoroutineScope(dispatcherProvider.provideDefaultDispatcher() + Job())
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

        val TODAY: LocalDate = LocalDate.now()
        val TOMORROW: LocalDate = LocalDate.ofEpochDay(TODAY.toEpochDay() + 1)

        val DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        val ZONE_OFFSET: ZoneOffset = OffsetDateTime.now().offset

        const val TASK_NAME_KEY = "task name"
        const val TASK_DESC_KEY = "task desc"
        const val TASK_ID_KEY = "task id"
        const val TASK_KEY = "task"
        const val TASK_CHANNEL_ID = "pending task Notifications channel"
        const val TASK_NOTIFICATION_ID = 0x1
        const val CREATE_NOTIFICATION_ACTION = "create notification action"

    }

}