package com.example.task1.retrofit

import com.example.task1.models.*
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

const val APIKEY = "96d31308896f028f63b8801331250f03"

@Singleton
class NetworkRepository @Inject constructor(private val api: NetworkApi) {
    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    suspend fun retrieveRequestToken(): StatusModel {
        return this.api.getRequestToken(APIKEY)
    }

    suspend fun login(user: LoginRequest): StatusModel {
        return this.api.postLogin(APIKEY, user)
    }

    suspend fun postSessionId(requestToken: String): StatusModel {
        return this.api.postSessionId(APIKEY, requestToken)
    }

    suspend fun getUserDetails(sessionId: String): UserModel {
        return this.api.getUserDetails(APIKEY, sessionId)
    }

    suspend fun invalidateSession(sessionId: String): Boolean {
        return this.api.invalidateSession(APIKEY, sessionId)
    }

    suspend fun retriveTrendingMoviesSeries(): PageMovieModel {
        return this.api.retriveTrendingMoviesSeries(APIKEY)
    }

    suspend fun retrivePopularPeople(language: String, page: Int): PopularPeople {
        return this.api.retrivePopularPeople(APIKEY, language, page)
    }

    suspend fun retriveTopRatedMovies(language: String, page: Int): PageMovieModel =
        this.api.retriveTopRatedMovies(APIKEY, language, page)

    suspend fun retrivePopularMovies(language: String, page: Int): PageMovieModel =
        this.api.retrivePopularMovies(APIKEY, language, page)

    suspend fun retriveAiringMovies(language: String, page: Int): PageMovieModel =
        this.api.retriveAiringMovies(APIKEY, language, page)

    suspend fun searchMovies(query: String?, language: String, page: Int): PageMovieModel =
        this.api.searchMovies(APIKEY, query, language, page)
}