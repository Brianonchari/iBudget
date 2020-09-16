package com.studycode.mymoneytracker.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.studycode.mymoneytracker.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(maintoolBar)
        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())
        val navController = Navigation.findNavController(this, R.id.navHostFragment)
        NavigationUI.setupWithNavController(nav_view, navController)
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.navHostFragment)
            , drawer_layout
        )
    }


//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.transactionsFragment -> {
//                true
//            }
//        }
//
//    }
}