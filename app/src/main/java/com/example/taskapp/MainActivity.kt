package com.example.taskapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.taskapp.databinding.ActivityMainBinding
import com.example.taskapp.di.AppComponent


class MainActivity : AppCompatActivity() {

    val appComponent: AppComponent by lazy { (application as MyApp).appComponent }

    private val navController: NavController
        get() = findNavController(this, R.id.nav_host_fragment)


    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val appBarConfig  = AppBarConfiguration(
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

        setupSideNav()
    }


    private fun setupSideNav() {
        binding?.sideNavView?.setupWithNavController(navController)
    }
    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }


    companion object


}