package com.example.task1.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task1.LoginActivity
import com.example.task1.db.MovieRepository
import com.example.task1.models.MovieEntity


class HomeViewModel() : ViewModel() {
    var repo = MovieRepository(LoginActivity())
    var allMovies = MutableLiveData<List<MovieEntity>>()

    init {
//        allMovies = repo.getAllMovies()!!
    }

    suspend fun insert(movieEntity: MovieEntity) {
        repo.insert(movieEntity)
    }

    suspend fun update(movieEntity: MovieEntity) {
        repo.update(movieEntity)
    }


    fun getAllMovies(): LiveData<List<MovieEntity>> {
        return allMovies
    }

}