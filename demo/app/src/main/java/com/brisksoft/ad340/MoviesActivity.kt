package com.brisksoft.ad340

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import java.util.*

class MoviesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)

        val navHostFragment =
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        Log.d("FRAGMENT expected", intent.getStringExtra("fragment").toString())
        val destination = if (
            intent.getStringExtra("fragment").toString() == "movies2"
        ) R.id.movieList2 else R.id.movieList1

        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)
        navGraph.setStartDestination(destination)
        navController.graph = navGraph

        val appBarConfiguration = AppBarConfiguration(navController.graph)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.setupWithNavController(navController, appBarConfiguration)

    }

}