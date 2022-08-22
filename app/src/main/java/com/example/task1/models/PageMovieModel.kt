package com.example.task1.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

const val TABLE_MOVIE = "movies_db"
const val LOGIN_MOVIE = "login_db"

@Serializable
data class PageMovieModel(

    @SerialName("page")
    val page: Int? = null,

    @SerialName("total_pages")
    val totalPages: Int? = null,

    @SerialName("results")
    val results: List<MovieResult> = emptyList(),

    @SerialName("total_results")
    val totalResults: Int? = null
)

@Serializable
data class MovieResult(
    @SerialName("overview")
    val overview: String = "",

    @SerialName("original_language")
    val originalLanguage: String = "",

    @SerialName("profile_path")
    val profile_path: String = "",

    val name: String = "",
    val gender: Int = 0,

    @SerialName("origin_country")
    val originCountry: List<String> = emptyList(),

    @SerialName("first_air_date")
    val firstAirDate: String = "",
    @SerialName("media_type")
    val mediaType: String = "",

    @SerialName("original_title")
    val originalTitle: String = "",

    @SerialName("video")
    val video: Boolean = false,

    @SerialName("title")
    val title: String = "",

    @SerialName("genre_ids")
    val genreIds: List<Int> = emptyList(),

    @SerialName("poster_path")
    val posterPath: String = "",

    @SerialName("backdrop_path")
    val backdropPath: String = "",

    @SerialName("original_name")
    val originalName: String = "",

    @SerialName("release_date")
    val releaseDate: String = "",

    @SerialName("known_for_department")
    val known_for_department: String = "",

    @SerialName("popularity")
    val popularity: Double = 0.0,

    @SerialName("vote_average")
    val voteAverage: Double = 0.0,

    @SerialName("known_for")
    val knowFor: List<MovieResult> = emptyList(),

    @SerialName("id")
    val id: Int = 0,

    @SerialName("adult")
    val adult: Boolean = false,

    @SerialName("vote_count")
    val voteCount: Int = 0
)
