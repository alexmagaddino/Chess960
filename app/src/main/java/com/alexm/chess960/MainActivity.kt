package com.alexm.chess960

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.chess960.chess960.R
import kotlinx.android.synthetic.main.main_activity.*


/**
 * Created by alexm on 18/03/2020
 */
class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private val navController by lazy {
        Navigation.findNavController(this, R.id.main_navigation_host)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        setSupportActionBar(toolbar)

        toolbar.navigationIcon = null
        toolbar.title = getString(R.string.app_name)
        supportActionBar?.title = getString(R.string.app_name)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        NavigationUI.setupWithNavController(nav_view, navController)
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)

        drawer_layout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination,
                                      arguments: Bundle?) {
    }
}
