package com.example.taskapp

import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.taskapp.di.AppComponent
import com.example.taskapp.workers.CreateNotificationBroadcastReceiver
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    val appComponent: AppComponent by lazy { (application as MyApp).appComponent }

    //    @Inject
    private val createCreateNotificationBroadcastReceiver: CreateNotificationBroadcastReceiver by lazy {
        CreateNotificationBroadcastReceiver()
    }

    private lateinit var appBarConfig: AppBarConfiguration
    private val noNavMenuDestinations =
        setOf(
            R.id.navigation_edit_task, R.id.navigation_task_details, R.id.navigation_add_reminder,
            R.id.navigation_add_task
        )


    override fun onCreate(savedInstanceState: Bundle?) {
//        appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = findNavController(R.id.nav_host_fragment)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        appBarConfig = AppBarConfiguration(
            setOf(R.id.navigation_my_tasks, R.id.navigation_today),
            findViewById(R.id.main_drawer_layout)
        )
        toolbar?.setupWithNavController(navController, appBarConfig)

        setupBottomNavMenu(navController)
        setupSideNav(navController)
        navController.addOnDestinationChangedListener(this)

        registerReceiver(
            createCreateNotificationBroadcastReceiver, IntentFilter(CREATE_NOTIFICATION_ACTION)
        )

    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(createCreateNotificationBroadcastReceiver)
    }

    override fun onDestinationChanged(
        controller: NavController, destination: NavDestination,
        arguments: Bundle?
    ) {
        if (noNavMenuDestinations.contains(destination.id)) {
            findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.visibility = View.GONE
        } else {
            findViewById<BottomNavigationView>(R.id.bottom_nav_view)?.visibility = View.VISIBLE
        }


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
        const val CREATE_NOTIFICATION_ACTION = "create notification action"
        const val TAG = "kruci"
        const val TODAY_FRAGMENT_INDEX = 0
        const val MY_TASKS_FRAGMENT_INDEX = 1
    }

}