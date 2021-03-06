package com.example.wantedapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.wantedapp.R
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView


class FeedActivity : AppCompatActivity() {

    private lateinit var toolbar : MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        var bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        var navController = findNavController(this, R.id.fragment)
        bottomNavigationView.setupWithNavController(navController)

        toolbar = findViewById(R.id.topBar)
        setSupportActionBar(toolbar)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         menuInflater.inflate(R.menu.menu_appbar,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.addPost -> {

                val intentB = Intent(this, PostAddActivity::class.java)
                startActivity(intentB)

                true
            }
            R.id.settings ->{
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> false
        }
        return true
    }
}