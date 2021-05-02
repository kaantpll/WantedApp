package com.example.wantedapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.wantedapp.models.Post

@Database(entities =[Post::class],version=1,exportSchema = false)
abstract class DatabaseHelper :RoomDatabase() {

    abstract fun dao() : PostDao

    companion object{
        @Volatile private var instance : DatabaseHelper? = null

        private var lock = Any()

        operator fun invoke(context : Context) = instance ?: synchronized(lock){
            instance ?: makeDatabase(context).also{

            }
        }

        private fun makeDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,DatabaseHelper::class.java,"postdb"
        ).build()
    }
}