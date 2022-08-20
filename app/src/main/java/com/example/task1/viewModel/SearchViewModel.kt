package com.example.task1.viewModel

import androidx.lifecycle.*
import com.example.task1.db.MoviesDao
import com.example.task1.models.MovieEntity
import com.example.task1.retrofit.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SearchViewModel(
    private val dao: MoviesDao,
    private val repo: LoginRepository
) : ViewModel() {

    private var job: Job = Job()

    private val _searchedMovies = MutableLiveData<List<MovieEntity?>>()
    val searched: LiveData<List<MovieEntity?>>
        get() {
            return _searchedMovies
        }

    fun update(id: Int) {
        job.cancel()
        val movie: MovieEntity = dao.getById(id)
        job = viewModelScope.launch(Dispatchers.IO) {
            movie.id?.let { dao.updateFields(it, movie.name, movie.image, movie.voteAvg) }
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
                it.title?.let { it1 ->
                    it.voteAverage?.let { it2 ->
                        MovieEntity(
                            id = it.id,
                            name = it1,
                            image = it.posterPath,
                            voteAvg = it2
                        )
                    }
                }
            }

            fullList.forEach { model ->
                if (model != null) {
                    model.id?.let { it1 -> dao.queryAfterId(it1) }?.let { movieEntity ->
                        model.isFavorite = movieEntity.isFavorite
                    }
                }
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