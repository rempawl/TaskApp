package com.example.taskapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.taskapp.di.AppComponent
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    val appComponent: AppComponent by lazy { (application as MyApp).appComponent }

    private lateinit var appBarConfig: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        appBarConfig = AppBarConfiguration(setOf(R.id.navigation_my_tasks, R.id.navigation_today))

        val navController = findNavController(R.id.nav_host_fragment)
        toolbar?.setupWithNavController(navController, appBarConfig)

        setupBottomNavMenu(navController)


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