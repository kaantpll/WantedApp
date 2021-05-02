package com.example.wantedapp.views

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.wantedapp.R
import com.example.wantedapp.adapters.PostAdapter
import com.example.wantedapp.databinding.FragmnetHomeBinding
import com.example.wantedapp.models.Post
import com.example.wantedapp.viewmodel.HomeFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class HomeFragment : Fragment(R.layout.fragmnet_home) {

    private val adapter = PostAdapter(arrayListOf())
    private val db = FirebaseFirestore.getInstance()
    var postList = ArrayList<Post>()
    private var fragmentbind: FragmnetHomeBinding? = null

    private lateinit var viewModel: HomeFragmentViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val bind = FragmnetHomeBinding.bind(view)
        fragmentbind = bind

        viewModel = ViewModelProvider(this).get(HomeFragmentViewModel::class.java)

        bind.postsRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        bind.postsRecyclerview.adapter = adapter

        getData()

    }

    private fun getData() {
        db.collection("Posts").orderBy("paylasimTarihi", Query.Direction.DESCENDING).addSnapshotListener { snap, e ->

            if (e != null) {
                Snackbar.make(requireView(), "While getting data error", 3000).show()
            } else {

                val posts = snap?.documents
                postList.clear()
                if (posts != null) {
                    for (post in posts) {
                        val kayipKisi = post.get("kayipKisi").toString()
                        val ilaniVeren = post.get("ilaniVeren").toString()
                        val imageUrl = post.get("imageUrl").toString()
                        val konum = post.get("konum").toString()
                        val yas = post.get("yas").toString()
                        val postId = post.get("postId").toString()
                        val tarih = post.get("tarih").toString()
                        val paylasimTarihi = post.get("paylasimTarihi").toString()
                        val description = post.get("description").toString()
                        val phone = post.get("phone").toString()
                        var postInstance = Post(kayipKisi, konum, paylasimTarihi, postId, tarih, yas, imageUrl, description, ilaniVeren, phone)

                        postList.add(postInstance)
                        adapter.postList = postList

                        if (postList.contains(post.id)) {

                        } else {
                            viewModel.addData(postList)

                        }
                    }
                }
                adapter.notifyDataSetChanged()
            }

        }

    }
}
