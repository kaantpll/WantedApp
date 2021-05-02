package com.example.wantedapp.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.wantedapp.R
import com.example.wantedapp.models.Post
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ProfileFragment  : Fragment(R.layout.fragment_profile){

    private var db : FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}