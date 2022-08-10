package com.example.task1.db

import android.content.Context
import androidx.room.Room

object MovieDBSingelton {
    var database: MoviesDB? = null

    fun getInstance(context: Context): MoviesDB? {
        if (database == null) {
            database = Room.databaseBuilder(context, MoviesDB::class.java, "movie_db")
                .fallbackToDestructiveMigration()
                .build()
        }
        return database
    }
}