package com.example.task1.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task1.db.MovieRepository
import com.example.task1.models.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

const val SEARCHVIEWMODEL = "SearchViewModel"

@HiltViewModel
class SearchViewModel @Inject constructor(private val repo: MovieRepository) : ViewModel() {

    private var job: Job = Job()

    private val _searchedMovies = MutableLiveData<List<MovieEntity>>()
    val searched: LiveData<List<MovieEntity>>
        get() {
            return _searchedMovies
        }

    fun update(movie: MovieEntity) {
        job.cancel()
        job = viewModelScope.launch(Dispatchers.Main) {
            repo.update(movie)
        }
    }

    fun searchQuery(query: String?) {
        job.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            _searchedMovies.postValue(repo.searchQuery(query))
        }
    }
}
