package com.example.wantedapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wantedapp.adapters.MessageAdapter
import com.example.wantedapp.models.Message
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class ChatActivity : AppCompatActivity() {


    private var db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var auth: FirebaseAuth? = FirebaseAuth.getInstance()
    private var user: FirebaseUser? = auth?.currentUser
    var messageList = ArrayList<Message>()
    private var adapter = MessageAdapter(arrayListOf())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        var chatRv = findViewById<RecyclerView>(R.id.chatRv)
        var enterMessageEditText = findViewById<EditText>(R.id.enterMessageArea)
        var sendMessageButton = findViewById<Button>(R.id.sendMessageButton)

        chatRv.layoutManager = LinearLayoutManager(this)
        chatRv.adapter = adapter
        chatRv.setHasFixedSize(true)

        sendMessageButton.setOnClickListener {
            val text = enterMessageEditText.text.toString()
            var username = "adsf"
            var imageUrl = "dsf"
            sendMessageFun(2,text,username,imageUrl)
        }

        getMessages()


    }

    private fun sendMessageFun(i: Int, text: String, username: String, imageUrl: String) {
        val myMessage = Message(i,text,username,imageUrl)

        db.collection("Message").add(myMessage).addOnCompleteListener { task->
            if(task.isSuccessful){
                Toast.makeText(applicationContext,"Sending",Toast.LENGTH_SHORT).show()
            }
        }?.addOnFailureListener { e->
            Toast.makeText(applicationContext,"Error",Toast.LENGTH_SHORT).show()

        }
    }


    private fun getMessages() {
        db.collection("Message").orderBy("date", Query.Direction.DESCENDING).addSnapshotListener { snapshot, e ->
            if (e != null) {
                e.printStackTrace()
            } else {
                if (snapshot != null) {
                    val messages = snapshot.documents

                    messageList.clear()

                    for (messageI in messages) {
                        val message = messageI.get("message").toString();

                        val myMessage = Message(1, message, "kaan", "aaa")

                        messageList.add(myMessage)
                        adapter.messageList = messageList
                    }
                    adapter.notifyDataSetChanged()
                }
            }
        }
    }
}