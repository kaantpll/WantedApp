package com.example.wantedapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    private var db : FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var user : FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)



        signUpBtn.setOnClickListener {
            var username = signUpUserNameText.text.toString()
            var email = signUpEmailText.text.toString()
            var password = signUpPassword.text.toString()
            var age = signUpAgeText.text.toString()
            var phoneNumber = signUpPhoneNumberText.text.toString()
            SignUp(username,password,email,age,phoneNumber)
        }
    }

    private fun SignUp(username: String, password: String,email:String,age:String,phoneNumber: String) {
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if(task.isSuccessful){
                user = auth.currentUser
                val intent = Intent(this,FeedActivity::class.java)
                intent.putExtra("a",phoneNumber)
                startActivity(intent)

                registerUserToDatabase(username,email,password,age,phoneNumber)
            }
        }.addOnFailureListener { e->
            Toast.makeText(this,e.message.toString(),Toast.LENGTH_LONG).show()
        }
    }


    private fun registerUserToDatabase(username: String, email: String, password: String, age: String, phoneNumber: String) {
        var userId = user.uid
        var user = hashMapOf(
            "userId" to userId,
            "username" to username,
            "email" to email,
            "password" to password,
            "age" to age,
            "phoneNumber" to phoneNumber,
        )

        db.collection("Users").add(user).addOnCompleteListener { task->
            if(task.isSuccessful) {
            Toast.makeText(this,"Register Successful",Toast.LENGTH_SHORT).show()

            }
            else{
                Toast.makeText(this,"Register Error !!",Toast.LENGTH_SHORT).show()
            }
        }
    }
}