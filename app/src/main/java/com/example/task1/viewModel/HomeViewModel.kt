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


    private var _airingMovies = MutableLiveData<List<MovieEntity>>()
    var airingMovies: LiveData<List<MovieEntity>> = repo.getAiringMovies()

    private var _popularMovies = MutableLiveData<List<MovieEntity>>()
    var popularMovies: LiveData<List<MovieEntity>> = repo.getPopularMovies()

    private var _topRatedMovies = MutableLiveData<List<MovieEntity>>()

    var topRated: LiveData<List<MovieEntity>> = repo.getTopRatedMovies()


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

//    private fun getTopRatedMovies() {
//        viewModelScope.launch(Dispatchers.IO) {
//             repo.getTopRatedMovies()
//        }
//    }
//
//    private fun getPopularMovies() {
//        viewModelScope.launch(Dispatchers.IO) {
//            _popularMovies= repo.getPopularMovies()
//        }
//    }
//
//    private fun getAiringMovies() {
//        viewModelScope.launch {
//            _airingMovies = repo.getAiringMovies()
//        }
//    }

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
//        getAiringMovies()
//        getPopularMovies()
        getViewPagerMovies()
        getStarsMovies()
        getCountries()
//        getTopRatedMovies()
        isLogged()
    }

}
