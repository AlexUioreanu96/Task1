package com.example.task1.retrofit

import com.example.task1.models.PageMovieModel
import com.example.task1.models.PopularPeople
import com.example.task1.models.StatusModel
import com.example.task1.models.UserModel
import retrofit2.http.*

interface LoginService {

    @GET("authentication/token/new")
    suspend fun getRequestToken(
        @Query("api_key") value: String
    ): StatusModel

    @POST("authentication/token/validate_with_login")
    suspend fun postLogin(
        @Query("api_key") value: String, @Body user: UserModel
    ): StatusModel


    @POST("authentication//session/new")
    suspend fun postSessionId(
        @Query("api_key") value: String, @Query("request_token") requestToken: String
    ): StatusModel

    @GET("account")
    suspend fun getUserDetails(
        @Query("api_key") value: String, @Query("session_id") sessionId: String
    ): UserModel

    @DELETE("authentication/session")
    suspend fun invalidateSession(
        @Query("api_key") value: String, @Query("session_id") sessionId: String
    ): Boolean

    @GET("trending/all/day")
    suspend fun retriveTrendingMoviesSeries(
        @Query("api_key") value: String
    ): PageMovieModel

    @GET("person/popular")
    suspend fun retrivePopularPeople(
        @Query("api_key") value: String,
        @Query("language") language: String,
        @Query("page") page: Int,
    ): PopularPeople

    @GET("movie/top_rated")
    suspend fun retriveTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): PageMovieModel


    @GET("movie/popular")
    suspend fun retrivePopularMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): PageMovieModel


    @GET("tv/airing_today")
    suspend fun retriveAiringMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): PageMovieModel

    @GET("search/multi")
    suspend fun searchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") query: String?,
        @Query("language") language: String,
        @Query("page") page: Int
    ): PageMovieModel

}