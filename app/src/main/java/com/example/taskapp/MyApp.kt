package com.example.taskapp

import android.app.Application
import com.example.taskapp.di.AppComponent
import com.example.taskapp.di.DaggerAppComponent

class MyApp : Application() {
    val appComponent : AppComponent by lazy { DaggerAppComponent.factory()
        .create(applicationContext)
    }
}