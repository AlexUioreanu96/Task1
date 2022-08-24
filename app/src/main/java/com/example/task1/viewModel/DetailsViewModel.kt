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

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repo: MovieRepository) : ViewModel() {

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


    fun setMovieId(id: Int) {
        job.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            loadData(repo.getById(id))
        }
    }

    private fun loadData(movie: MovieEntity?) {
        _title.postValue(movie?.name)
        _image.postValue("https://image.tmdb.org/t/p/w500${movie?.image}")
        _vote.postValue(movie?.voteAvg)
    }
}
