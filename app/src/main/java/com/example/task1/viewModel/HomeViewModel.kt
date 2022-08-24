package com.example.task1.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task1.CountriesQuery
import com.example.task1.apolloClient
import com.example.task1.db.MovieRepository
import com.example.task1.models.ImagesModel
import com.example.task1.models.MovieEntity
import com.example.task1.models.Star
import com.example.task1.models.StatusModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: MovieRepository) : ViewModel() {


    var isLogged: Boolean = false


    private val _airingMovies = MutableLiveData<List<MovieEntity>>()
    val airingMovies: LiveData<List<MovieEntity>>
        get() {
            return _airingMovies
        }

    private val _popularMovies = MutableLiveData<List<MovieEntity>>()
    val popularMovies: LiveData<List<MovieEntity>>
        get() {
            return _popularMovies
        }

    private val _topRatedMovies = MutableLiveData<List<MovieEntity>>()
    val topRated: LiveData<List<MovieEntity>>
        get() {
            return _topRatedMovies
        }

    private val _starsMovie = MutableLiveData<List<Star>>()
    val starsMovie: LiveData<List<Star>>
        get() {
            return _starsMovie
        }

    private var _countries = MutableLiveData<List<CountriesQuery.Country>>()
    val countries: LiveData<List<CountriesQuery.Country>>
        get() {
            return _countries
        }

    private val _viewPager = MutableLiveData<List<ImagesModel>>()
    val viewPager: LiveData<List<ImagesModel>>
        get() {
            return _viewPager
        }

    init {
        loadStuff()
    }

    fun update(movie: MovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.update(movie)
        }
    }

    private fun getViewPagerMovies() {
        viewModelScope.launch {
            _viewPager.postValue(repo.getViewPagerMovies())
        }
    }

    private fun getCountries() {
        viewModelScope.launch(Dispatchers.Main) {
            _countries.postValue(apolloClient.query(CountriesQuery()).execute().data?.countries)
        }
    }


    private fun getStarsMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _starsMovie.postValue(repo.getStarsMovies())
        }
    }

    private fun getTopRatedMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _topRatedMovies.postValue(repo.getTopRatedMovies())
        }
    }

    private fun getPopularMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            _popularMovies.postValue(repo.getPopularMovies())
        }
    }

    private fun getAiringMovies() {
        viewModelScope.launch {
            _airingMovies.value = repo.getAiringMovies()
        }
    }

    private fun isLogged() {
        var tokenStatus: StatusModel? = null
        viewModelScope.launch(Dispatchers.IO) {
            tokenStatus = repo.getStatusModel()
            isLogged = tokenStatus != null
        }
    }

    suspend fun logOut() {
        repo.logOut()
    }

    private fun loadStuff() {
        getAiringMovies()
        getPopularMovies()
        getViewPagerMovies()
        getStarsMovies()
        getCountries()
        getTopRatedMovies()
        isLogged()
    }

}
