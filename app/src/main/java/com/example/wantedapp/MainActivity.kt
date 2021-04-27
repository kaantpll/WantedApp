package com.example.wantedapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class MainActivity : AppCompatActivity() {
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    private var user = auth.currentUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signInBtn.setOnClickListener {
            var username = signInUserNameText.text.toString()
            var password = signInPassword.text.toString()
            SignIn(username,password)
        }
        goToSignUpText.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
                startActivity(intent)
        }

        if(user!=null){
            val intent = Intent(this,FeedActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
    private fun SignIn(username: String, password: String) {
        auth.signInWithEmailAndPassword(username,password).addOnCompleteListener { task->
            if(task.isSuccessful){
                val intent = Intent(this,FeedActivity::class.java)
                startActivity(intent)
            }
        }.addOnFailureListener {
            Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show()
        }
    }
}