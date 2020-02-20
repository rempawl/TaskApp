package com.example.taskapp

import android.app.Application
import com.example.taskapp.di.AppComponent
import com.example.taskapp.di.DaggerAppComponent
import com.jakewharton.threetenabp.AndroidThreeTen

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

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }


































}