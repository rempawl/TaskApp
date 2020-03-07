package com.example.taskapp

import android.app.Application
import com.example.taskapp.di.AppComponent
import com.example.taskapp.di.DaggerAppComponent
import com.example.taskapp.workers.WorkersInitializer
import com.jakewharton.threetenabp.AndroidThreeTen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import javax.inject.Inject

//todo changing updateDate
//todo mockK
//todo editTask init reminder state radio check
//todo settings menu
//todo tests
//todo myTasks switchMap
//todo Realization Entity
//todo realization btn for  task without reminders

class MyApp : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory()
            .create(applicationContext,this)
    }
    @Inject
    lateinit var workersInitializer: WorkersInitializer

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        appComponent.inject(this)
        delayedInit()
    }


    private fun delayedInit() {
        applicationScope.launch {
            AndroidThreeTen.init(this@MyApp)
            workersInitializer.setUpWorkers(context = applicationContext)
        }
    }

    companion object{
        const val PREFERENCES_NAME = "com.example.taskapp"
        val TODAY: LocalDate = LocalDate.now()
        val TOMORROW: LocalDate = LocalDate.ofEpochDay(TODAY.toEpochDay() + 1)
    }

}