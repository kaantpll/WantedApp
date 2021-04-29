package com.example.wantedapp.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.wantedapp.R
import com.example.wantedapp.databinding.FragmentDescriptionBinding
import com.example.wantedapp.models.Post

class DescriptionFragment : Fragment(R.layout.fragment_description) {

    private val args : DescriptionFragmentArgs by  navArgs()
    private var fragmentBinding :FragmentDescriptionBinding? = null
    private lateinit var currentpost : Post

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        currentpost = args.post!!
        val bind = FragmentDescriptionBinding.bind(view)
        fragmentBinding = bind

       bind.descriptionText.text = currentpost.description

    }
}