package com.example.wantedapp

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.CalendarContract
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.collection.LLRBNode
import kotlinx.android.synthetic.main.activity_feed.*

class FeedActivity : AppCompatActivity() {

    private lateinit var toolbar : MaterialToolbar
    private var settingsActivity = SettingsActivity()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feed)

        var navController = findNavController(this,R.id.fragment)
        bottomNavigationView.setupWithNavController(navController)

        toolbar = findViewById(R.id.topBar)
        setSupportActionBar(toolbar)



/*
        val intent = Intent()
        val check = intent.getBooleanExtra("check",false)

        Toast.makeText(this,check.toString(),Toast.LENGTH_SHORT).show()
        if(!check){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
*/




    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         menuInflater.inflate(R.menu.menu_appbar,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.addPost -> {
                val intent = Intent(this, PostAddActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.settings ->{
                val intent = Intent(this,SettingsActivity::class.java)
                startActivity(intent)
                true
            }
            else -> false
        }
        return true
    }
}