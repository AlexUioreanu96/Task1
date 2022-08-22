package com.example.task1.viewModel

import androidx.lifecycle.*
import com.example.task1.CountriesQuery
import com.example.task1.MovieApplication
import com.example.task1.apolloClient
import com.example.task1.db.MovieRepository
import com.example.task1.models.ImagesModel
import com.example.task1.models.MovieEntity
import com.example.task1.models.Star
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class HomeViewModel(
    private val repo: MovieRepository
) : ViewModel() {

    private var job: Job = Job()

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

    fun update(movie: MovieEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.update(movie)
        }
    }

    private fun getViewPagerMovies() {
        job = viewModelScope.launch(Dispatchers.IO) {
            _viewPager.postValue(repo.getViewPagerMovies())
        }
    }

    private fun getCountries() {
        job = viewModelScope.launch(Dispatchers.Main) {
            _countries.postValue(apolloClient.query(CountriesQuery()).execute().data?.countries)
        }
    }


    private fun getStarsMovies() {
        job = viewModelScope.launch(Dispatchers.IO) {
            _starsMovie.postValue(repo.getStarsMovies())
        }
    }

    private fun getTopRatedMovies() {
        job = viewModelScope.launch(Dispatchers.IO) {
            _topRatedMovies.postValue(repo.getTopRatedMovies())
        }
    }

    private fun getPopularMovies() {
        job = viewModelScope.launch(Dispatchers.IO) {
            _popularMovies.postValue(repo.getPopularMovies())
        }
    }

    private fun getAiringMovies() {
        job = viewModelScope.launch(Dispatchers.IO) {
            _airingMovies.postValue(repo.getAiringMovies())
        }
    }

    fun loadStuff() {
        getAiringMovies()
        getPopularMovies()
        getViewPagerMovies()
        getStarsMovies()
        getCountries()
        getTopRatedMovies()
    }
}


@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val application: MovieApplication
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(application.repository) as T
    }
}