package com.example.coinswatchermvvm.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.coinswatchermvvm.R
import kotlinx.android.synthetic.main.activity_coinswatchermvvm.*


class CoinsWatcherMVVMActivity : AppCompatActivity() {

    private val LOG_TAG = "CoinsWatcherMVVMActivity_debugLog_"

    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {

        Log.d(LOG_TAG,"CoinsWatcherMVVMActivity onCreate: MAIN_ACTIVITY_ON_CREATE_THIS: $this")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coinswatchermvvm)

        setSupportActionBar(toolbar)

        navController = Navigation.findNavController(this,
            R.id.nav_host_fragment
        )

        bottom_nav.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this, navController)
//        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp( navController , null)
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(LOG_TAG,"CoinsWatcherMVVMActivity onRestart: MAIN_ACTIVITY_ON_RESTART_THIS: $this")
    }

    override fun onResume() {
        super.onResume()
        Log.d(LOG_TAG,"CoinsWatcherMVVMActivity onResume: MAIN_ACTIVITY_ON_RESUME_THIS: $this")
    }
}

/*

https://stackoverflow.com/questions/41718633/bottomnavigationview-with-more-than-3-items-tab-title-is-hiding



 */