package com.example.taskapp

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
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {
    val appComponent: AppComponent by lazy { (application as MyApp).appComponent }


    private lateinit var appBarConfig: AppBarConfiguration
    private val noNavMenuDestinations =
        setOf(
            R.id.navigation_edit_task, R.id.navigation_task_details, R.id.navigation_add_reminder,
            R.id.navigation_add_task, R.id.navigation_pick_custom_delay
        )


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navController = findNavController(R.id.nav_host_fragment)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        appBarConfig = AppBarConfiguration(
            setOf(
                R.id.navigation_my_tasks,
                R.id.navigation_today,
                R.id.navigation_pick_custom_delay
            ),
            findViewById(R.id.main_drawer_layout)
        )
        toolbar?.setupWithNavController(navController, appBarConfig)

        setupBottomNavMenu(navController)
        setupSideNav(navController)
        navController.addOnDestinationChangedListener(this)


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

    /*  override fun onSupportNavigateUp(): Boolean {
          val navController = findNavController(R.id.nav_host_fragment)
          val curDest = navController.currentDestination
          val predicate = curDest?.id == R.id.navigation_edit_task || curDest?.id == R.id.navigation_add_reminder
          return if(predicate){
              Toast.makeText(applicationContext,"ludada",Toast.LENGTH_SHORT)
                  .show()
              super.onSupportNavigateUp()
          }else{
              navController.navigateUp()
          }
      }*/

    companion object {
        const val TAG = "kruci"
    }


}