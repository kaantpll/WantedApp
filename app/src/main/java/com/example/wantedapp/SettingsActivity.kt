package com.example.wantedapp

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color.RED
import android.hardware.camera2.params.RggbChannelVector.RED
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.google.firebase.auth.FirebaseAuth
import com.google.type.Color
import kotlinx.android.synthetic.main.activity_settings.*
import kotlinx.coroutines.*

class SettingsActivity : AppCompatActivity() {

    private var auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        signOut.setOnClickListener {
            auth.signOut()
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val appSettingsPrefers : SharedPreferences = getSharedPreferences("AppSettings",0)
        val sharedPreferencesEdit : SharedPreferences.Editor = appSettingsPrefers.edit()
        val isNightModeOn : Boolean = appSettingsPrefers.getBoolean("NightMode",false)

       switchDarkMode.setOnClickListener {
           if(isNightModeOn){
               sharedPreferencesEdit.putBoolean("NightMode",false)
               sharedPreferencesEdit.apply()
               intent.putExtra("check",false)

           }
           else{

               sharedPreferencesEdit.putBoolean("NightMode",true)
               sharedPreferencesEdit.apply()
               intent.putExtra("check",true)


           }
       }


        backSettings.setOnClickListener {
            val intent = Intent(this,FeedActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}