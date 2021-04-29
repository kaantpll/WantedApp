package com.example.wantedapp

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_post_add.*
import java.util.*

class PostAddActivity : AppCompatActivity() {

    var selectedPictureFromGallery : Uri? = null
    var selectedBitmap : Bitmap? = null
    private  var mAuth : FirebaseAuth = FirebaseAuth.getInstance()
    private lateinit var storage : FirebaseStorage
    private lateinit var db : FirebaseFirestore
    private var user = mAuth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_add)

        mAuth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        db = FirebaseFirestore.getInstance()

        backHomePage.setOnClickListener {
            val intent = Intent(this,FeedActivity::class.java)
        startActivity(intent)}

        imageView.setOnClickListener {
            selectImage()
        }

        val postId = user.uid


        ekle_button.setOnClickListener {
            var kayipKisi = kayip_edittext.text.toString()
            var tarih = tarih_edittext.text.toString()
            var yas = yas_edittext.text.toString()
            var konum = konum_edittext.text.toString()
            var description = description.text.toString()
            var ilaniVeren = kayip_edittext.text.toString()
            var phone = phoneNumber.text.toString()
            sharePost(kayipKisi, tarih, yas, konum, description, ilaniVeren, postId,phone)
            if(kayipKisi.isEmpty()||tarih.isEmpty()|| yas.isEmpty() || konum.isEmpty() || description.isEmpty() || ilaniVeren.isEmpty()){
                MaterialAlertDialogBuilder(this)
                    .setTitle("Hata")
                    .setMessage("Boş Alan Bırakmayınız !!!")
                    .setNeutralButton("Cancel") { dialog, which ->
                    }
                    .setPositiveButton("Kabul") { dialog, which ->
                    }
                    .show()
            }

        }
    }
    private fun sharePost(kayipKisi : String,tarih:String,yas:String,konum:String,description: String,ilaniVeren:String,postId:String,phone:String) {

        val uuid = UUID.randomUUID()
        val imageName = "${uuid}.jpg"

        val reference = storage.reference

        val pictureReferance = reference.child("images").child(imageName)

        if (selectedPictureFromGallery != null) {
            pictureReferance.putFile(selectedPictureFromGallery!!).addOnSuccessListener { task ->
                val loadedPictureReferance =
                    FirebaseStorage.getInstance().reference.child("images").child(imageName)
                loadedPictureReferance.downloadUrl.addOnSuccessListener { uri ->
                    val url = uri.toString()
                    val date = Timestamp.now()

                    val post = hashMapOf(
                        "postId" to postId,
                        "kayipKisi" to kayipKisi,
                        "tarih" to tarih,
                        "yas" to yas,
                        "imageUrl" to url,
                        "konum" to konum,
                        "description" to description,
                        "ilaniVeren" to ilaniVeren,
                        "paylasimTarihi" to date,
                         "phone" to phone

                    )
                    db.collection("Posts").add(post).addOnCompleteListener { task ->
                        if (task.isSuccessful){
                            Toast.makeText(this,"Success", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        }
                    }.addOnFailureListener { e->
                        Toast.makeText(this,"Hata", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


    private fun selectImage() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),1)
        } else {

            val galeriIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(galeriIntent,2)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == 1){
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){

                val galeriIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntent,2)
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 2 && resultCode == Activity.RESULT_OK && data != null) {

            selectedPictureFromGallery = data.data

            if (selectedPictureFromGallery != null) {

                if(Build.VERSION.SDK_INT >= 28) {

                    val source = ImageDecoder.createSource(this.contentResolver,selectedPictureFromGallery!!)
                    selectedBitmap = ImageDecoder.decodeBitmap(source)
                    imageView.setImageBitmap(selectedBitmap)

                } else {
                    selectedBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver,selectedPictureFromGallery)
                    imageView.setImageBitmap(selectedBitmap)
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}