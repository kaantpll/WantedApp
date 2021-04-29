package com.example.wantedapp.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.wantedapp.R
import com.example.wantedapp.models.Post
import com.example.wantedapp.views.HomeFragmentDirections
import com.squareup.picasso.Picasso


class PostAdapter(var postList : ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.MyViewHolder>(){

    class MyViewHolder(var view : View)  : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_item,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentPost = postList[position]
        var card_name = holder.view.findViewById<TextView>(R.id.card_name)
        var card_konum = holder.view.findViewById<TextView>(R.id.card_konum)
        var card_ = holder.view.findViewById<TextView>(R.id.card_)
        var card_yas = holder.view.findViewById<TextView>(R.id.card_yas)
        var card_ilani_veren = holder.view.findViewById<TextView>(R.id.card_ilani_veren)
        var card_image = holder.view.findViewById<ImageView>(R.id.card_image)
        var call = holder.view.findViewById<Button>(R.id.call)
        card_name.text = postList[position].kayipKisi
        card_konum.text = postList[position].konum
        card_.text = postList[position].tarih
        card_yas.text = postList[position].yas
        card_ilani_veren.text = postList[position].ilaniVeren
        Picasso.get().load(postList[position].imageUrl).into(card_image)
        val no = postList[position].phone
        call.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$no")
            it.context.startActivity(intent)
        }

        holder.itemView.setOnClickListener { view->
            val direction = HomeFragmentDirections.actionHomeToDescriptionFragment(currentPost)
            view.findNavController().navigate(direction)

        }

    }

    override fun getItemCount(): Int {
       return postList.size
    }
}