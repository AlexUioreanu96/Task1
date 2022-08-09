package com.example.task1.db

import androidx.room.*
import com.example.task1.models.MovieEntity


import com.example.task1.models.TABLE_NAME

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(data: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOne(data: MovieEntity)

    @Query("DELETE FROM $TABLE_NAME")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TABLE_NAME")
    suspend fun getAll(): List<MovieEntity>

    @Update
    suspend fun update(lastMinuteProduct: MovieEntity)

    @Query("SELECT * FROM $TABLE_NAME WHERE id = :id")
    suspend fun queryAfterId(id: String): MovieEntity


    @Query("UPDATE $TABLE_NAME SET isFavorite = 0 WHERE id = :id")
    fun removeFavorite(id: Int?)
}