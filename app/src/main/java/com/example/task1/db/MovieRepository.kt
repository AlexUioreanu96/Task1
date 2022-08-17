package com.example.task1.db

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.task1.models.MovieEntity
import com.example.task1.retrofit.LoginRepository

class MovieRepository(context: Context) {

    private var retrofit: LoginRepository = LoginRepository()
    private var dao: MoviesDao? = MovieDBSingelton.getInstance(context)?.getMovieDB()

    private var allMovies = dao?.getAll()

    suspend fun insert(movieEntity: MovieEntity) {
        dao?.insertOne(movieEntity)
    }

    suspend fun update(movieEntity: MovieEntity) {
        movieEntity.id?.let {
            dao?.updateFields(
                it,
                movieEntity.name,
                movieEntity.image,
                movieEntity.voteAvg
            )
        }
    }


    fun delete(movieEntity: MovieEntity) {

    }

    fun getAllMovies(): LiveData<List<MovieEntity>>? {
        return allMovies
    }

}