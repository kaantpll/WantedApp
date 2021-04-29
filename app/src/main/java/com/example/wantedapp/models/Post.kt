package com.example.wantedapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Post(
        var kayipKisi : String,
        var konum : String,
        var paylasimTarihi : String,
        var postId:String,
        var tarih : String,
        var yas : String,
        var imageUrl : String,
        var description:String,
        var ilaniVeren : String,
        var phone :String,

):Parcelable