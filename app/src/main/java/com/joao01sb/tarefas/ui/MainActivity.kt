package com.joao01sb.tarefas.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.joao01sb.tarefas.R
import com.joao01sb.tarefas.notification.CHANNEL


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        NavigationUI.setupActionBarWithNavController(this, navHostFragment.navController)
        createNotificationChannel()
    }

    private fun scheduleNotification() {

    }

    private fun createNotificationChannel() {
        val name = "Notif Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> findNavController(R.id.nav_host_fragment_content_main).popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

//    private fun setupNavigation() {
//        // As we're inside a fragment calling `findNavController()` directly will crash the app
//        // Hence, get a reference of `NavHostFragment`
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
//
//        // set navigation controller
//        navController = navHostFragment.findNavController()
//
//        // appbar configuration (for back button)
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//        setupActionBarWithNavController(navController, appBarConfiguration)
//    }

}