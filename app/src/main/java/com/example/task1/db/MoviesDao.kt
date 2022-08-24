package com.example.task1.db


import androidx.room.*
import com.example.task1.models.LOGIN_MOVIE
import com.example.task1.models.MovieEntity
import com.example.task1.models.StatusModel
import com.example.task1.models.TABLE_MOVIE

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertAll(data: List<MovieEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOne(data: MovieEntity)

    @Query("DELETE FROM $TABLE_MOVIE")
    suspend fun deleteAll()

    @Query("SELECT * FROM $TABLE_MOVIE")
    suspend fun getAll(): List<MovieEntity>

    @Query("SELECT * FROM $TABLE_MOVIE WHERE :movieId = id")
    suspend fun getById(movieId: Int): MovieEntity

    @Update
    suspend fun update(movie: MovieEntity)

    @Query("UPDATE $TABLE_MOVIE SET name=:name, image = :image, voteAvg =:voteAvg WHERE id =:id ")
    suspend fun updateFields(id: Int, name: String?, image: String?, voteAvg: Double?)

    @Query("SELECT * FROM $TABLE_MOVIE WHERE id = :id")
    suspend fun queryAfterId(id: Int): MovieEntity?

    @Query("SELECT * FROM $TABLE_MOVIE WHERE trending = :trend")
    suspend fun getAllTrend(trend: Int): List<MovieEntity>

    @Query("SELECT * FROM $TABLE_MOVIE WHERE name LIKE :query")
    suspend fun getAllbyQuery(query: String?): List<MovieEntity>

    @Query("UPDATE $TABLE_MOVIE SET isFavorite = 0 WHERE id = :id")
    suspend fun removeFavorite(id: Int?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToken(data: StatusModel)

    @Query("SELECT * FROM $LOGIN_MOVIE  WHERE 1 = id")
    suspend fun getStatusModel(): StatusModel


}