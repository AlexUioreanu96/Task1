package com.example.task1.db

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.task1.models.*
import com.example.task1.retrofit.LoginRepository

const val MOVIEREPO = "Movie Respository"

class MovieRepository(context: Context) {

    private var repo: LoginRepository = LoginRepository()
    private var dao: MoviesDao? = MovieDBSingelton.getInstance(context).getMovieDB()

    private var allMovies = dao?.getAll()

    suspend fun getViewPagerMovies(): List<ImagesModel> {
        val imageModel: List<ImagesModel> =
            repo.retriveTrendingMoviesSeries().results.map {
                ImagesModel(
                    imageUrl = "https://image.tmdb.org/t/p/w500${it.backdropPath}",
                    releaseDate = it.releaseDate
                )
            }.take(6)
        return imageModel
    }

    suspend fun getStarsMovies(): List<Star> {
        val stars: List<Star> =
            repo.retrivePopularPeople("en-US", 1).results.map {
                Star(
                    it.name,
                    it.profilePath
                )
            }
        return stars
    }

    suspend fun getTopRatedMovies(): List<MovieEntity>? {
        val movieEntities: List<MovieEntity?> =
            repo.retriveTopRatedMovies("en-US", 1).results.map {
                MovieEntity(
                    id = it.id,
                    name = it.title,
                    image = it.posterPath,
                    voteAvg = it.voteAverage,
                    trending = 1
                )
            }
        synchronized(movieEntities)
        return dao?.getAllTrend(1)
    }

    suspend fun getPopularMovies(): List<MovieEntity>? {
        val movieEntities: List<MovieEntity?> =
            repo.retriveTopRatedMovies("en-US", 1).results.map {
                MovieEntity(
                    id = it.id,
                    name = it.title,
                    image = it.posterPath,
                    voteAvg = it.voteAverage,
                    trending = 2
                )
            }
        synchronized(movieEntities)
        return dao?.getAllTrend(2)
    }

    suspend fun getAiringMovies(): List<MovieEntity>? {
        val movieEntities: List<MovieEntity?> =
            repo.retriveTopRatedMovies("en-US", 1).results.map {
                MovieEntity(
                    id = it.id,
                    name = it.title,
                    image = it.posterPath,
                    voteAvg = it.voteAverage,
                    trending = 3
                )
            }
        synchronized(movieEntities)
        return dao?.getAllTrend(3)
    }

    suspend fun getById(id: Int): MovieEntity? {
        return dao?.getById(id)
    }


    suspend fun insert(movieEntity: MovieEntity) {
        dao?.insertOne(movieEntity)
    }

    suspend fun update(movieEntity: MovieEntity) {
        movieEntity.id.let {
            dao?.updateFields(
                it,
                movieEntity.name,
                movieEntity.image,
                movieEntity.voteAvg
            )
        }
    }

    suspend fun searchQuery(query: String?): List<MovieEntity> {
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
                model.id.let { it1 -> dao?.queryAfterId(it1) }?.let { movieEntity ->
                    model.isFavorite = movieEntity.isFavorite
                }
            }
        } catch (e: Exception) {
            Log.w(MOVIEREPO, "it catch: ${e.message}")
        }
        return fullList
    }


    fun delete(movieEntity: MovieEntity) {

    }

    fun getAllMovies(): LiveData<List<MovieEntity>>? {
        return allMovies
    }

    private suspend fun synchronized(list: List<MovieEntity?>) {
        list.forEach {
            val movie = it?.id?.let { it1 -> dao?.queryAfterId(it1) }
            if (movie != null) {
                dao?.updateFields(it.id, it.name, it.image, it.voteAvg)
            } else {
                if (it != null) {
                    dao?.insertOne(it)
                }
            }
        }
    }

    suspend fun getStatusModel() = dao?.getStatusModel()

    suspend fun login(userName: String, pass: String): StatusModel {
        val statusRetrive = repo.retrieveRequestToken()

        val loginRequest = LoginRequest(
            username = userName,
            password = pass,
            requestToken = statusRetrive.requestToken
        )

        return repo.login(loginRequest)
    }


    suspend fun insertToken(statusModel: StatusModel) {
        dao?.insertToken(statusModel)
    }

//    suspend fun state(userName: String, pass: String): LoginViewModel {
//        try {
//            var tokenStatus: StatusModel? = null
//
//            tokenStatus = dao?.getStatusModel()
//
//
//            if (dao.getStatusModel())
//
//            //Request Token
//            val statusRetrive = repo.retrieveRequestToken()
//
//            val loginRequest = LoginRequest(
//                username = userName,
//                password = pass,
//                requestToken = statusRetrive.requestToken
//            )
//
//            val statusLogin: StatusModel = repo.login(loginRequest)
//
//
//
//            if (statusLogin.success == true) {
//                _state.postValue(LoginViewModel.LoginState.Success)
//            } else {
//                _state.postValue(LoginViewModel.LoginState.Error("Username or password doesn't match"))
//            }
//        } catch (e: IOException) {
//            _state.postValue(LoginViewModel.LoginState.Error("Network error, please try again"))
//            Log.w("loginViewModel", "Error while login", e)
//        } catch (e: HttpException) {
//            _state.postValue(LoginViewModel.LoginState.Error("Username or password doesn't match"))
//            Log.w("loginViewModel", "Error while login", e)
//        }
//    }
}