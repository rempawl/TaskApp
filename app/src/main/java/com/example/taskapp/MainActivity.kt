package com.example.taskapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.taskapp.databinding.ActivityMainBinding
import com.example.taskapp.di.AppComponent


class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {
    val appComponent: AppComponent by lazy { (application as MyApp).appComponent }


    private lateinit var appBarConfig: AppBarConfiguration
    private val noNavMenuDestinations =
        setOf(
            R.id.navigation_edit_task, R.id.navigation_task_details, R.id.navigation_add_reminder,
            R.id.navigation_add_task, R.id.navigation_pick_custom_delay
        )

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(this,R.id.nav_host_fragment)
        val toolbar = binding.toolbar

        setSupportActionBar(toolbar)

        appBarConfig = AppBarConfiguration(
            setOf(
                R.id.navigation_my_tasks,
                R.id.navigation_today,
                R.id.navigation_pick_custom_delay
            ),
            binding.mainDrawerLayout
        )
        toolbar.setupWithNavController(navController, appBarConfig)

        setupBottomNavMenu(navController)
        setupSideNav(navController)
        navController.addOnDestinationChangedListener(this)

    }


    override fun onDestinationChanged(
        controller: NavController, destination: NavDestination,
        arguments: Bundle?
    ) {
        if (noNavMenuDestinations.contains(destination.id)) {
            binding.bottomNavView?.visibility = View.GONE
        } else {
            binding.bottomNavView?.visibility = View.VISIBLE
        }


    }


    private fun setupSideNav(navController: NavController) {
        binding.sideNavView
            ?.setupWithNavController(navController)
    }

    private fun setupBottomNavMenu(navController: NavController) {
        binding.bottomNavView
            ?.setupWithNavController(navController)
    }



    companion object {
        const val TAG = "kruci"
    }


}