package com.example.wantedapp.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.wantedapp.R
import com.example.wantedapp.models.Post
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_item.view.*

class PostAdapter(var postList : ArrayList<Post>) : RecyclerView.Adapter<PostAdapter.MyViewHolder>(){

    class MyViewHolder(var view : View)  : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_item,parent,false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.card_name.text = postList[position].kayipKisi
        holder.view.card_konum.text = postList[position].konum
        holder.view.card_.text = postList[position].tarih
        holder.view.card_yas.text = postList[position].yas
        holder.view.card_ilani_veren.text = postList[position].ilaniVeren
        Picasso.get().load(postList[position].imageUrl).into(holder.view.card_image)
        val no = postList[position].phone
        holder.view.call.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$no")
            it.context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
       return postList.size
    }
}