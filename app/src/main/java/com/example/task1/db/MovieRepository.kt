package com.example.task1.db

import android.util.Log
import androidx.lifecycle.LiveData
import com.example.task1.models.*
import com.example.task1.retrofit.NetworkRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

const val MOVIEREPO = "Movie Respository"


class MovieRepository @Inject constructor(
    private val repo: NetworkRepository,
    private val dao: MoviesDao,
) {

    val scope = CoroutineScope(Dispatchers.IO)

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

    fun getTopRatedMovies(): LiveData<List<MovieEntity>> {
        scope.launch {
            val movieEntitie =
                repo.retriveTopRatedMovies("en-US", 1).results.map {
                    MovieEntity(
                        id = it.id,
                        name = it.title,
                        image = it.posterPath,
                        voteAvg = it.voteAverage,
                        trending = 1
                    )
                }.filter { it.name.isNotEmpty() }

            synchronized(movieEntitie)
        }

        return dao.getAllTrend(1)
    }

    fun getPopularMovies(): LiveData<List<MovieEntity>> {
        scope.launch {
            val movieEntitie: List<MovieEntity> =
                repo.retrivePopularMovies("en-US", 1).results.map {
                    MovieEntity(
                        id = it.id,
                        name = it.title,
                        image = it.posterPath,
                        voteAvg = it.voteAverage,
                        trending = 2
                    )
                }.filter { it.name.isNotEmpty() }

            synchronized(movieEntitie)
        }
        return dao.getAllTrend(2)
    }


    fun getAiringMovies(): LiveData<List<MovieEntity>> {
        scope.launch {
            val movieEntities: List<MovieEntity> =
                repo.retriveAiringMovies("en-US", 1).results.map {
                    MovieEntity(
                        id = it.id,
                        name = it.title,
                        image = it.posterPath,
                        voteAvg = it.voteAverage,
                        trending = 3
                    )
                }.filter { it.name.isNotEmpty() }
            synchronized(movieEntities)
        }
        return dao.getAllTrend(3)
    }

    suspend fun getById(id: Int): MovieEntity {
        return dao.getById(id)
    }


    suspend fun insert(movieEntity: MovieEntity) {
        dao.insertOne(movieEntity)
    }

    suspend fun update(movieEntity: MovieEntity) {
        dao.update(movieEntity)
//        movieEntity.id.let {
//            dao.updateFields(
//                it,
//                movieEntity.name,
//                movieEntity.image,
//                movieEntity.voteAvg,
//
//                )
//        }
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
                model.id.let { it1 -> dao.queryAfterId(it1) }.let { movieEntity ->
                    model.isFavorite = movieEntity?.isFavorite
                }
            }
        } catch (e: Exception) {
            Log.w(MOVIEREPO, "it catch: ${e.message}")
        }
        return fullList
    }


//    private fun synchronized(list: List<MovieEntity>) {
//        list.forEach {
//            dao.insertOne(it)
//        }
//    }

    private suspend fun synchronized(list: List<MovieEntity>) {
        list.forEach {
            val movie = it.id.let { it1 -> dao.queryAfterId(it1) }
            if (movie != null) {
                dao.updateFields(it.id, it.name, it.image, it.voteAvg)
            } else {
                dao.insertOne(it)
            }
        }
    }

    suspend fun getStatusModel() = dao.getStatusModel()

    suspend fun login(userName: String, pass: String): StatusModel {
        val statusRetrive = repo.retrieveRequestToken()

        val loginRequest = LoginRequest(
            username = userName,
            password = pass,
            requestToken = statusRetrive.requestToken
        )

        return repo.login(loginRequest)
    }

    suspend fun logOut() {
        dao.logOut()
    }

    suspend fun insertToken(statusModel: StatusModel) {
        dao.insertToken(statusModel)
    }

}