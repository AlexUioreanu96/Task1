package com.example.task1.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

const val TABLE_NAME = "movies_db"

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
    val overview: String? = null,

    @SerialName("original_language")
    val originalLanguage: String? = null,

    @SerialName("profile_path")
    val profile_path: String? = null,

    val name: String = "",
    val gender: Int = 0,

    @SerialName("origin_country")
    val originCountry: List<String> = emptyList(),

    @SerialName("first_air_date")
    val firstAirDate: String? = null,
    @SerialName("media_type")
    val mediaType: String? = null,

    @SerialName("original_title")
    val originalTitle: String? = null,

    @SerialName("video")
    val video: Boolean? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("genre_ids")
    val genreIds: List<Int?>? = null,

    @SerialName("poster_path")
    val posterPath: String = "",

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("original_name")
    val originalName: String = "",

    @SerialName("release_date")
    val releaseDate: String? = null,

    @SerialName("known_for_department")
    val known_for_department: String? = null,

    @SerialName("popularity")
    val popularity: Double? = null,

    @SerialName("vote_average")
    val voteAverage: Double? = null,

    @SerialName("known_for")
    val knowFor: List<MovieResult> = emptyList(),

    @SerialName("id")
    val id: Int? = null,

    @SerialName("adult")
    val adult: Boolean? = null,

    @SerialName("vote_count")
    val voteCount: Int? = null
)
