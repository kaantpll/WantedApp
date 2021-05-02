package com.example.wantedapp.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "posts")
@Parcelize
data class Post(
        @ColumnInfo(name = "kayipKisi")
        var kayipKisi : String,
        @ColumnInfo(name = "konum")
        var konum : String,
        @ColumnInfo(name = "paylasimTarihi")
        var paylasimTarihi : String,
        @ColumnInfo(name = "postId")
        var postId:String,
        @ColumnInfo(name = "tarih")
        var tarih : String,
        @ColumnInfo(name = "yas")
        var yas : String,
        @ColumnInfo(name = "mimageUrl")
        var mimageUrl : String,
        @ColumnInfo(name = "description")
        var description:String,
        @ColumnInfo(name = "milaniVeren")
        var milaniVeren : String,
        @ColumnInfo(name = "phone")
        var phone :String,

        @PrimaryKey(autoGenerate = true)
        var uid : Int =0

):Parcelable