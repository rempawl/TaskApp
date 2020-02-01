package com.example.taskapp

import  androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.taskapp.di.AppComponent

class MainActivity : AppCompatActivity() {

    val appComponent : AppComponent by lazy{ (application as MyApp).appComponent }

    private lateinit var appBarConfig :AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        appBarConfig = AppBarConfiguration(setOf(R.id.navigation_home))

        val navController = findNavController(R.id.nav_host_fragment)
        toolbar?.setupWithNavController(navController,appBarConfig)

    }

    companion object{
        const val TAG = "kruci"
        const val TODAY_FRAGMENT_INDEX = 0
        const val MY_TASKS_FRAGMENT_INDEX = 1
        const val ADD_TASK_FRAGMENT_INDEX = 2
    }
}