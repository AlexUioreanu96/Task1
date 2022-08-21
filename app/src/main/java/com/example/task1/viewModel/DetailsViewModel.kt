package com.example.task1.viewModel

import androidx.lifecycle.*
import com.example.task1.db.MoviesDao
import com.example.task1.models.MovieEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetailsViewModel(private val dao: MoviesDao) : ViewModel() {

    private var job: Job = Job()

    private val _image = MutableLiveData<String>()
    val image: LiveData<String>
        get() {
            return _image
        }

    private val _title = MutableLiveData<String>()
    val title: LiveData<String>
        get() {
            return _title
        }

    private val _vote = MutableLiveData<Double>()
    val vote: LiveData<Double>
        get() {
            return _vote
        }
//
//    private val _starsMovie = MutableLiveData<List<Star>>()
//    val starsMovie: LiveData<List<Star>>
//        get() {
//            return _starsMovie
//        }


    fun getMovieById(id: Int) {
        job.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            val movie = dao.getById(id)
            loadData(movie)
        }
    }

    private fun loadData(movie: MovieEntity) {
        _title.postValue(movie.name)
        _image.postValue("https://image.tmdb.org/t/p/w500${movie.image}")
        _vote.postValue(movie.voteAvg)
    }
}

@Suppress("UNCHECKED_CAST")
class DetailsViewModelFactory(
    private val dao: MoviesDao
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailsViewModel(dao) as T
    }
}