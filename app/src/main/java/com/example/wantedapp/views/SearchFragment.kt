package com.example.wantedapp.views

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wantedapp.R
import com.example.wantedapp.adapters.SearchAdapter
import com.example.wantedapp.databinding.FragmentSearchBinding
import com.example.wantedapp.models.Post
import com.example.wantedapp.viewmodel.HomeFragmentViewModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore

class SearchFragment : Fragment(R.layout.fragment_search){

    private var fragmentBind : FragmentSearchBinding? = null
    private var db : FirebaseFirestore = FirebaseFirestore.getInstance()
    private lateinit var viewModel : HomeFragmentViewModel
    private var searchAdapter = SearchAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var bind = FragmentSearchBinding.bind(view)
        fragmentBind = bind

        viewModel  = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)



        bind.searchRv.layoutManager = LinearLayoutManager(requireContext())
        bind.searchRv.adapter = searchAdapter

        bind.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
               if(newText != null){
                   searchNote(newText)
               }
                return true
            }

        })


    }
    private fun searchNote(query: String){
        val searchQuery = "%$query%"
        viewModel.search(searchQuery).observe(viewLifecycleOwner, Observer {
                list->
            searchAdapter.postListAdapter = list
            Log.d("TAG",list.toString())
        })

    }

}