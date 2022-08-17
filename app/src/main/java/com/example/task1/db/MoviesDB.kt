package com.example.task1.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.task1.models.MovieEntity

@Database(
    entities = [MovieEntity::class],
    version = 2,
    exportSchema = false
)
abstract class MoviesDB : RoomDatabase() {

    abstract fun getMovieDB(): MoviesDao?

}