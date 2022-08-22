package com.example.task1.db

import android.content.Context
import androidx.room.Room

object MovieDBSingelton {
    private lateinit var database: MoviesDB

    fun getInstance(context: Context): MoviesDB {
        if (!this::database.isInitialized) {
            database = Room.databaseBuilder(context, MoviesDB::class.java, "movie_db")
                .fallbackToDestructiveMigration()
                .build()
        }
        return database
    }
}