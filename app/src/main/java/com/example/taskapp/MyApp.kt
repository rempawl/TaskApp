package com.example.taskapp

import android.app.Application
import androidx.room.Room
import com.example.taskapp.database.AppDataBase
import com.example.taskapp.di.AppComponent
import com.example.taskapp.di.DaggerAppComponent
import com.example.taskapp.di.DataBaseModule

class MyApp : Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory()
            .create(
                applicationContext,
                Room.databaseBuilder(applicationContext,AppDataBase::class.java,AppDataBase.DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build()
                )
   }
//        val mod  = DataBaseModule(applicationContext)
}