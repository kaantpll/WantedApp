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

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardUsername: TextView = view.findViewById(R.id.userNameChat)
        val cardMessage :TextView= view.findViewById(R.id.messageChat)
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
        holder.cardMessage.text = message.message

    }

    override fun getItemCount(): Int = messageList.size


    override fun getItemViewType(position: Int): Int {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        return if (messageList[position].id == firebaseUser!!.uid) {
            MESSAGE_TYPE_RIGHT
        } else {
            MESSAGE_TYPE_LEFT
        }

    }

}

