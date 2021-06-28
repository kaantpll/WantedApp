package com.example.wantedapp.views

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.wantedapp.R
import com.example.wantedapp.database.PostDao
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
        val sharedPref : SharedPreferences =
            requireContext().getSharedPreferences("PREFERENCE_NAME", Context.MODE_PRIVATE)


        fragmentBinding = binding

        imageView = view.findViewById(R.id.userImage)

        imageView.setOnClickListener {
            selectImage()

        }
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

    private fun selectImage() {
        if (ContextCompat.checkSelfPermission(requireActivity(), android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {

            val galeriIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galeriIntent, 2)
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                val galeriIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntent, 2)
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {

            selectedPictureFromGallery = data.data

            if (selectedPictureFromGallery != null) {

                if (Build.VERSION.SDK_INT >= 28) {

                    val source = ImageDecoder.createSource(requireContext().contentResolver, selectedPictureFromGallery!!)
                    selectedBitmap = ImageDecoder.decodeBitmap(source)
                    imageView.setImageBitmap(selectedBitmap)


                } else {
                    selectedBitmap = MediaStore.Images.Media.getBitmap(requireContext().contentResolver, selectedPictureFromGallery)
                    imageView.setImageBitmap(selectedBitmap)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}