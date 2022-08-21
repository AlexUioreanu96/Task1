package com.example.task1.viewModel

import android.util.Log
import androidx.lifecycle.*
import com.example.task1.db.MoviesDao
import com.example.task1.models.MovieEntity
import com.example.task1.retrofit.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

const val SEARCHVIEWMODEL = "SearchViewModel"

class SearchViewModel(
    private val dao: MoviesDao,
    private val repo: LoginRepository
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
            dao.update(movie)
        }
    }

    fun searchQuery(query: String?) {
        job.cancel()

        job = viewModelScope.launch(Dispatchers.IO) {
            val fullList = (repo.searchMovies(query, "en-US", 1).results +
                    repo.searchMovies(
                        query,
                        "en-US",
                        2
                    ).results).filter { it.posterPath.isNotEmpty() }.map {
                MovieEntity(
                    id = it.id,
                    name = it.title,
                    image = it.posterPath,
                    voteAvg = it.voteAverage
                )
            }

            try {
                fullList.forEach { model ->
                    model.id?.let { it1 -> dao.queryAfterId(it1) }?.let { movieEntity ->
                        model.isFavorite = movieEntity.isFavorite
                    }
                }
            } catch (e: Exception) {
                Log.w(SEARCHVIEWMODEL, "it catch")
            }
            _searchedMovies.postValue(fullList)

        }
    }
}

@Suppress("UNCHECKED_CAST")
class SearchViewModelFactory(
    private val dao: MoviesDao,
    private val repo: LoginRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchViewModel(dao, repo) as T
    }
}