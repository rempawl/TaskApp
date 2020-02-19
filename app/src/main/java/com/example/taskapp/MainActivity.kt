package com.example.taskapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.taskapp.di.AppComponent
import com.example.taskapp.utils.SetAlarmsWorker
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    val appComponent: AppComponent by lazy { (application as MyApp).appComponent }

    private lateinit var appBarConfig: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setUpAlarm()


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        appBarConfig = AppBarConfiguration(
            setOf(R.id.navigation_my_tasks, R.id.navigation_today),
            findViewById(R.id.main_drawer_layout)
        )

        val navController = findNavController(R.id.nav_host_fragment)
        toolbar?.setupWithNavController(navController, appBarConfig)

        setupBottomNavMenu(navController)
        setupSideNav(navController)

    }

    private fun setUpAlarm() {
        val setAlarmsRequest  =
            PeriodicWorkRequestBuilder<SetAlarmsWorker>(1,TimeUnit.DAYS)
            .build()

        WorkManager.getInstance(applicationContext)
            .enqueue(setAlarmsRequest)



    }

    private fun setupSideNav(navController: NavController) {
        findViewById<NavigationView>(R.id.side_nav_view)
            ?.setupWithNavController(navController)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        findViewById<BottomNavigationView>(R.id.bottom_nav_view)
            ?.setupWithNavController(navController)
    }

    companion object {
        const val TAG = "kruci"
        const val TODAY_FRAGMENT_INDEX = 0
        const val MY_TASKS_FRAGMENT_INDEX = 1
    }
}