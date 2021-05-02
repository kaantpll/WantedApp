package com.example.wantedapp.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.wantedapp.R

import com.example.wantedapp.models.Message
import com.example.wantedapp.util.Direction.MESSAGE_TYPE_LEFT
import com.example.wantedapp.util.Direction.MESSAGE_TYPE_RIGHT
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.squareup.picasso.Picasso


class MessageAdapter(var messageList: ArrayList<Message>) :
        RecyclerView.Adapter<MessageAdapter.MyViewHolder>() {


    private lateinit var firebaseUser: FirebaseUser

    class MyViewHolder(var view: View) : RecyclerView.ViewHolder(view) {
        val cardUsername = view.findViewById<TextView>(R.id.userNameChatRight)
        val cardImageView = view.findViewById<ShapeableImageView>(R.id.card_left_image)
        val cardMessage = view.findViewById<TextView>(R.id.messageChatLeft)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return if (viewType == MESSAGE_TYPE_RIGHT) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_right, parent, false)
            MyViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.card_left, parent, false)
            MyViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val message = messageList[position]
        holder.cardUsername.text = message.userName
        Picasso.get().load(message.imageUrl).into(holder.cardImageView)
        holder.cardMessage.text = message.userName

    }

    override fun getItemCount(): Int = messageList.size


    override fun getItemViewType(position: Int): Int {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        if (messageList[position].id.toString() == firebaseUser!!.uid) {
            return MESSAGE_TYPE_RIGHT
        } else {
            return MESSAGE_TYPE_LEFT
        }

    }

}

