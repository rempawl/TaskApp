package com.example.taskapp

import android.app.Application
import com.example.taskapp.di.AppComponent
import com.example.taskapp.di.DaggerAppComponent
import com.jakewharton.threetenabp.AndroidThreeTen

//todo changing updateDate
//todo mockK
//todo editTask init
//todo save editTask
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
//fun atoi(str: String) : Int{
//    //minus   whitespace ' ' plus
//    var num = 0
//    var sign =1
//    str.forEach {c ->
//        when (c) {
//            '-' -> sign =-1
//            '+' -> sign =1
//            in '0'..'9' -> {
//                num = num* 10 +  (c-'0')
//            }
//        }
//
//    }
//
//    return 0
//}