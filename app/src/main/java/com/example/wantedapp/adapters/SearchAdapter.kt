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
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wantedapp.R
import com.example.wantedapp.models.Post
import com.example.wantedapp.views.HomeFragmentDirections
import com.squareup.picasso.Picasso

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.MyViewHolder>() {

    class MyViewHolder(var view : View)  : RecyclerView.ViewHolder(view)

    private val diffUtil = object : DiffUtil.ItemCallback<Post>(){
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

    }

    private val recyclerViewDiff = AsyncListDiffer(this,diffUtil)
    var postListAdapter : List<Post>
    get() = recyclerViewDiff.currentList
    set(value) = recyclerViewDiff.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_search,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var card_name = holder.view.findViewById<TextView>(R.id.card_name)
        var card_konum = holder.view.findViewById<TextView>(R.id.card_konum)
        var card_ = holder.view.findViewById<TextView>(R.id.card_)
        var card_yas = holder.view.findViewById<TextView>(R.id.card_yas)
        var card_ilani_veren = holder.view.findViewById<TextView>(R.id.card_ilani_veren)
        var card_image = holder.view.findViewById<ImageView>(R.id.card_image)



        val postList = postListAdapter[position]
        Picasso.get().load(postList.mimageUrl).into(card_image)
        var call = holder.view.findViewById<Button>(R.id.call)
        val no = postList.phone
        call.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$no")
            it.context.startActivity(intent)
        }


        holder.view.apply {
            card_name.text = "${postList.kayipKisi}"
            card_konum.text ="${postList.konum}"
            card_.text = "${postList.tarih}"
            card_yas.text = "${postList.yas}"
            card_ilani_veren.text = "${postList.milaniVeren}"
        }
    }

    override fun getItemCount(): Int {
     return postListAdapter.size

    }

  /*

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {





        var card_ilani_veren = holder.view.findViewById<TextView>(R.id.card_ilani_veren)
        var card_image = holder.view.findViewById<ImageView>(R.id.card_image)
        var call = holder.view.findViewById<Button>(R.id.call)



        Picasso.get().load(postList[position].mimageUrl).into(card_image)
        val no = postList[position].phone
        call.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:$no")
            it.context.startActivity(intent)
        }

        /*holder.itemView.setOnClickListener { view->
            val direction = HomeFragmentDirections.actionHomeToDescriptionFragment(currentPost)
            view.findNavController().navigate(direction)

        }*/

    }

    override fun getItemCount(): Int {
        return postList.size
    }

    fun updateList(newList : ArrayList<Post>){
        postList.clear()
        newList.addAll(newList)
        notifyDataSetChanged()
    }
*/
}
