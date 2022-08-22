package com.example.task1.viewModel

import androidx.lifecycle.*
import com.example.task1.MovieApplication
import com.example.task1.db.MovieRepository
import com.example.task1.models.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

const val SEARCHVIEWMODEL = "SearchViewModel"

class SearchViewModel(
    private val repo: MovieRepository
) : ViewModel() {

    private var job: Job = Job()

    private val _searchedMovies = MutableLiveData<List<MovieEntity>>()
    val searched: LiveData<List<MovieEntity>>
        get() {
            return _searchedMovies
        }

    fun update(movie: MovieEntity) {
        job.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
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

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory(
    private val application: MovieApplication
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(application.repository) as T
    }
}