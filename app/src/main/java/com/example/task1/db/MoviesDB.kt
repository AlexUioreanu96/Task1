package com.example.task1.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.task1.models.MovieEntity
import com.example.task1.models.StatusModel

@Database(
    entities = [MovieEntity::class, StatusModel::class],
    version = 5,
    exportSchema = false
)
abstract class MoviesDB : RoomDatabase() {

    abstract fun getMovieDB(): MoviesDao?

}