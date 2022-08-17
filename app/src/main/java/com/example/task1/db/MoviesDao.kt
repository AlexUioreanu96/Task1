package com.example.task1.db


import androidx.lifecycle.LiveData
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
    fun getAll(): LiveData<List<MovieEntity>>

    @Update
    suspend fun update(lastMinuteProduct: MovieEntity)

    @Query("UPDATE $TABLE_NAME SET name=:name, image = :image, voteAvg =:voteAvg WHERE id =:id ")
    suspend fun updateFields(id: Int, name: String?, image: String?, voteAvg: Double?)

    @Query("SELECT * FROM $TABLE_NAME WHERE id = :id")
    suspend fun queryAfterId(id: Int): MovieEntity

    @Query("SELECT * FROM $TABLE_NAME WHERE trending = :trend")
    suspend fun getAllTrend(trend: Int): List<MovieEntity>

    @Query("SELECT * FROM $TABLE_NAME WHERE name LIKE :query")
    suspend fun getAllbyQuery(query: String?): List<MovieEntity>


    @Query("UPDATE $TABLE_NAME SET isFavorite = 0 WHERE id = :id")
    fun removeFavorite(id: Int?)
}