package com.example.task1.viewModel

import androidx.lifecycle.*
import com.example.task1.CountriesQuery
import com.example.task1.apolloClient
import com.example.task1.db.MoviesDao
import com.example.task1.models.ImagesModel
import com.example.task1.models.MovieEntity
import com.example.task1.models.Star
import com.example.task1.retrofit.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class HomeViewModel(
    private val repo: LoginRepository,
    private val dao: MoviesDao
) : ViewModel() {

    init {
        getAiringMovies()
        getPopularMovies()
        getViewPagerMovies()
        getStarsMovies()
        getCountries()
        getTopRatedMovies()
    }

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

    fun update(id: Int) {
        val movie: MovieEntity = dao.getById(id)
        job = viewModelScope.launch(Dispatchers.IO) {
            movie.id?.let { dao.updateFields(it, movie.name, movie.image, movie.voteAvg) }
        }
    }


    fun getViewPagerMovies() {

        job = viewModelScope.launch(Dispatchers.IO) {
            val imageModel: List<ImagesModel> =
                repo.retriveTrendingMoviesSeries().results.map {
                    ImagesModel(
                        imageUrl = "https://image.tmdb.org/t/p/w500${it.backdropPath}",
                        releaseDate = it.releaseDate
                    )
                }.take(6)

            _viewPager.postValue(imageModel)
        }
    }

    fun getCountries() {
        job = viewModelScope.launch(Dispatchers.Main) {
            _countries.postValue(apolloClient.query(CountriesQuery()).execute().data?.countries)
        }
    }


    fun getStarsMovies() {

        job = viewModelScope.launch(Dispatchers.IO) {
            val stars: List<Star> =
                repo.retrivePopularPeople("en-US", 1).results.map {
                    Star(
                        it.name,
                        it.profilePath
                    )
                }
            _starsMovie.postValue(stars)
        }
    }

    fun getTopRatedMovies() {
        job = viewModelScope.launch(Dispatchers.IO) {
            val movieEntities: List<MovieEntity?> =
                repo.retriveTopRatedMovies("en-US", 1).results.map {
                    it.title?.let { it1 ->
                        it.voteAverage?.let { it2 ->
                            MovieEntity(
                                id = it.id,
                                name = it1,
                                image = it.posterPath,
                                voteAvg = it2,
                                trending = 1
                            )
                        }
                    }
                }

            synchronized(movieEntities)
            val list = dao.getAllTrend(1)

            _topRatedMovies.postValue(list)

        }
    }

    fun getPopularMovies() {
        job = viewModelScope.launch(Dispatchers.IO) {
            val movieEntities: List<MovieEntity?> =
                repo.retrivePopularMovies("en-US", 1).results.map {
                    it.voteAverage?.let { it1 ->
                        it.title?.let { it2 ->
                            MovieEntity(
                                id = it.id,
                                name = it2,
                                image = it.posterPath,
                                voteAvg = it1,
                                trending = 2
                            )
                        }
                    }
                }

            synchronized(movieEntities)
            val list = dao.getAllTrend(2)

            _popularMovies.postValue(list)

        }
    }

    fun getAiringMovies() {
        job = viewModelScope.launch(Dispatchers.IO) {
            val movieEntities: List<MovieEntity?> =
                repo.retriveAiringMovies("en-US", 1).results.map {
                    it.title?.let { it1 ->
                        it.voteAverage?.let { it2 ->
                            MovieEntity(
                                id = it.id,
                                name = it1,
                                image = it.posterPath,
                                voteAvg = it2,
                                trending = 3
                            )
                        }
                    }
                }

            synchronized(movieEntities)
            val list = dao.getAllTrend(3)

            _airingMovies.postValue(list)

        }
    }

    private suspend fun synchronized(list: List<MovieEntity?>) {
        list.forEach {
            val movie = it?.id?.let { it1 -> dao.queryAfterId(it1) }
            if (movie != null) {
                dao.updateFields(it.id, it.name, it.image, it.voteAvg)
            } else {
                if (it != null) {
                    dao.insertOne(it)
                }
            }
        }
    }
}


@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(
    private val repo: LoginRepository,
    private val dao: MoviesDao
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repo, dao) as T
    }
}