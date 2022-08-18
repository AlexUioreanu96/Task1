package com.example.task1.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.task1.LoginActivity
import com.example.task1.db.MovieRepository
import com.example.task1.models.MovieEntity


class HomeViewModel() : ViewModel() {
    var repo = MovieRepository(LoginActivity())

    var allMovies = MutableLiveData<List<MovieEntity>>()

    init {

    }



}