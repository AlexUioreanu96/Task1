package com.example.task1.db

import android.util.Log
import com.example.task1.models.*
import com.example.task1.retrofit.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

const val MOVIEREPO = "Movie Respository"


class MovieRepository @Inject constructor(
    private val repo: NetworkRepository,
    private val dao: MoviesDao
) {

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

    suspend fun getTopRatedMovies(): List<MovieEntity> {
        val movieEntitie: List<MovieEntity> =
            repo.retriveTopRatedMovies("en-US", 1).results.map {
                MovieEntity(
                    id = it.id,
                    name = it.title,
                    image = it.posterPath,
                    voteAvg = it.voteAverage,
                    trending = 1
                )
            }
        synchronized(movieEntitie)
        val list = dao.getAllTrend(1)
        return movieEntitie
    }

    suspend fun getPopularMovies(): List<MovieEntity> {
        val movieEntities1: List<MovieEntity> =
            repo.retrivePopularMovies("en-US", 1).results.map {
                MovieEntity(
                    id = it.id,
                    name = it.title,
                    image = it.posterPath,
                    voteAvg = it.voteAverage,
                    trending = 2
                )
            }
        synchronized(movieEntities1)

        val list = dao.getAllTrend(2)
        println("dasd")
        return list
    }

    suspend fun getAiringMovies(): List<MovieEntity> = withContext(Dispatchers.IO) {
        val movieEntities: List<MovieEntity> =
            repo.retriveAiringMovies("en-US", 1).results.map {
                MovieEntity(
                    id = it.id,
                    name = it.title,
                    image = it.posterPath,
                    voteAvg = it.voteAverage,
                    trending = 3
                )
            }
        synchronized(movieEntities)
        dao.getAllTrend(3)

        movieEntities
    }

    suspend fun getById(id: Int): MovieEntity {
        return dao.getById(id)
    }


    suspend fun insert(movieEntity: MovieEntity) {
        dao.insertOne(movieEntity)
    }

    suspend fun update(movieEntity: MovieEntity) {
        movieEntity.id.let {
            dao.updateFields(
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
                model.id.let { it1 -> dao.queryAfterId(it1) }.let { movieEntity ->
                    model.isFavorite = movieEntity?.isFavorite
                }
            }
        } catch (e: Exception) {
            Log.w(MOVIEREPO, "it catch: ${e.message}")
        }
        return fullList
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