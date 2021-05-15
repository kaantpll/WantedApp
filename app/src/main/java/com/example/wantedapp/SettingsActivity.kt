package com.example.wantedapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Switch
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*

class SettingsActivity : AppCompatActivity() {

    private var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        var signOut = findViewById<ImageView>(R.id.signOut)
        var switchDarkMode = findViewById<SwitchMaterial>(R.id.switchDarkMode)
        var backSettings = findViewById<ImageView>(R.id.backSettings)

        signOut.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val appSettingsPrefers: SharedPreferences = getSharedPreferences("AppSettings", Context.MODE_PRIVATE)
        val sharedPreferencesEdit: SharedPreferences.Editor = appSettingsPrefers.edit()
        val isNightModeOn: Boolean = appSettingsPrefers.getBoolean("NightMode", false)

        switchDarkMode.setOnClickListener {
            if (isNightModeOn) {
                setTheme(R.style.Theme_WantedApp_Dark)
                sharedPreferencesEdit.putBoolean("NightMode", true)
                sharedPreferencesEdit.apply()

            } else {
                setTheme(R.style.Theme_WantedApp)
                sharedPreferencesEdit.putBoolean("NightMode", false)
                sharedPreferencesEdit.apply()

            }
        }


        backSettings.setOnClickListener {
            val intent = Intent(this, FeedActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}