package com.example.task1.retrofit

import com.example.task1.models.*
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

const val APIKEY = "96d31308896f028f63b8801331250f03"

class LoginRepository {
    private val json = Json {
        coerceInputValues = true
        ignoreUnknownKeys = true
    }

    @OptIn(ExperimentalSerializationApi::class)
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

    val service = retrofit.create(LoginApi::class.java)

    suspend fun retrieveRequestToken(): StatusModel {
        return service.getRequestToken(APIKEY)
    }

    suspend fun login(user: LoginRequest): StatusModel {
        return service.postLogin(APIKEY, user)
    }

    suspend fun postSessionId(requestToken: String): StatusModel {
        return service.postSessionId(APIKEY, requestToken)
    }

    suspend fun getUserDetails(sessionId: String): UserModel {
        return service.getUserDetails(APIKEY, sessionId)
    }

    suspend fun invalidateSession(sessionId: String): Boolean {
        return service.invalidateSession(APIKEY, sessionId)
    }

    suspend fun retriveTrendingMoviesSeries(): PageMovieModel {
        return service.retriveTrendingMoviesSeries(APIKEY)
    }

    suspend fun retrivePopularPeople(language: String, page: Int): PopularPeople {
        return service.retrivePopularPeople(APIKEY, language, page)
    }

    suspend fun retriveTopRatedMovies(language: String, page: Int): PageMovieModel =
        service.retriveTopRatedMovies(APIKEY, language, page)

    suspend fun retrivePopularMovies(language: String, page: Int): PageMovieModel =
        service.retrivePopularMovies(APIKEY, language, page)

    suspend fun retriveAiringMovies(language: String, page: Int): PageMovieModel =
        service.retriveAiringMovies(APIKEY, language, page)

    suspend fun searchMovies(query: String?, language: String, page: Int): PageMovieModel =
        service.searchMovies(APIKEY, query, language, page)
}