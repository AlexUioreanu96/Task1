package com.example.task1.retrofit

import com.example.task1.models.PageMovieModel
import com.example.task1.models.PopularPeople
import com.example.task1.models.StatusModel
import com.example.task1.models.UserModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val APIKEY = "96d31308896f028f63b8801331250f03"

class LoginClientRetrofit {

    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.themoviedb.org/3/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service = retrofit.create(LoginService::class.java)

    suspend fun retrieveRequestToken(): StatusModel {
        return service.getRequestToken(APIKEY)
    }

    suspend fun login(user: UserModel): StatusModel {
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