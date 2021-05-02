package com.example.wantedapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.wantedapp.models.Post

@Dao
interface PostDao {

    @Insert
    suspend fun insertAll(posts : List<Post>)

    @Query("DELETE  FROM posts")
    suspend fun delete()

    @Query("SELECT * from posts")
    fun getData() : LiveData<List<Post>>

    @Query("SELECT * FROM posts where kayipKisi LIKE :query OR milaniVeren LIKE:query")
    fun searchPost(query : String) : LiveData<List<Post>>

}