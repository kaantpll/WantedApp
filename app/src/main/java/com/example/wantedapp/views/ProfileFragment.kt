package com.example.wantedapp.views

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.example.wantedapp.R
import com.example.wantedapp.databinding.FragmentProfileBinding
import com.example.wantedapp.models.Post
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.squareup.picasso.Picasso

class ProfileFragment  : Fragment(R.layout.fragment_profile){

    private var db : FirebaseFirestore = FirebaseFirestore.getInstance()
    var selectedPictureFromGallery: Uri? = null
    var selectedBitmap: Bitmap? = null
    private lateinit var imageView: ImageView

    private var fragmentBinding : FragmentProfileBinding? = null
    private var auth : FirebaseAuth = FirebaseAuth.getInstance()
    private var user : FirebaseUser = auth.currentUser


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentProfileBinding.bind(view)
        fragmentBinding = binding

        getData()

    }

    private fun getData()
    {
        db.collection("Users").addSnapshotListener{
            snap, e ->
            if(e != null){
                print("$e")
            }
            else{
                if(snap != null){
                    val posts = snap?.documents
                    for(post in posts){
                        val name  = post.get("username").toString()
                        val email = post.get("email").toString()
                        val age = post.get("age").toString()
                        val phoneNumber = post.get("phoneNumber").toString()
                        val userId =  post.get("userId").toString()

                        if(user.uid == userId){
                            fragmentBinding?.profileName?.text = "Name : $name"
                            fragmentBinding?.profileEmail?.text = "Email: $email"
                            fragmentBinding?.profileAge?.text = "Age: $age"
                            fragmentBinding?.profilePhoneNumber?.text = "Phone: $phoneNumber"

                        }
                    }
                }
            }
        }
    }
}