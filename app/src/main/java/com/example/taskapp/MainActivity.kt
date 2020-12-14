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

    private val navController: NavController
        get() = findNavController(this, R.id.nav_host_fragment)

    private lateinit var appBarConfig: AppBarConfiguration
    private val noNavMenuDestinations =
        setOf(
            R.id.navigation_edit_task, R.id.navigation_task_details, R.id.navigation_add_task,
            R.id.navigation_add_task, R.id.navigation_pick_custom_delay
        )

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        appBarConfig = AppBarConfiguration(
            setOf(
                R.id.navigation_my_tasks,
                R.id.navigation_today,
                R.id.navigation_pick_custom_delay
            ),
            binding?.mainContainer
        )

        val toolbar = binding?.toolbar
        setSupportActionBar(toolbar)
        toolbar?.setupWithNavController(navController, appBarConfig)

        setupBottomNavMenu()
        setupSideNav()
        navController.addOnDestinationChangedListener(this)
    }


    override fun onDestinationChanged(
        controller: NavController, destination: NavDestination, arguments: Bundle?
    ) {
        binding?.run {
            if (noNavMenuDestinations.contains(destination.id)) {
                sideNavView.visibility = View.GONE
            } else {
                sideNavView.visibility = View.VISIBLE
            }
        }
    }


    private fun setupSideNav() {
        binding?.sideNavView?.setupWithNavController(navController)
    }

    private fun setupBottomNavMenu() {
//        binding?.bottomNavView?.setupWithNavController(navController)
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }


    companion object {
        const val TAG = "kruci"
    }


}