package com.joao01sb.tarefas.ui

import android.app.*
import android.content.Context
import android.content.Intent
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
import com.joao01sb.tarefas.notification.*
import com.joao01sb.tarefas.notification.Notification
import java.util.*


class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as NavHostFragment
        NavigationUI.setupActionBarWithNavController(this, navHostFragment.navController)
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