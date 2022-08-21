package com.example.task1.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PopularPeople(

    @SerialName("page")
    val page: Int = 0,

    @SerialName("total_pages")
    val totalPages: Int = 0,

    @SerialName("results")
    val results: List<People> = emptyList(),

    @SerialName("total_results")
    val totalResults: Int = 0
)

@Serializable
data class Star(val name: String?, val profilePath: String?)

@Serializable
data class People(

    @SerialName("gender")
    val gender: Int = 0,

    @SerialName("known_for_department")
    val knownForDepartment: String = "",

    @SerialName("known_for")
    val knownFor: List<KnownForItem> = emptyList(),

    @SerialName("popularity")
    val popularity: Double = 0.0,

    @SerialName("name")
    val name: String = "",

    @SerialName("profile_path")
    val profilePath: String = "",

    @SerialName("id")
    val id: Int = 0,

    @SerialName("adult")
    val adult: Boolean = false
)

@Serializable
data class KnownForItem(

    @SerialName("overview")
    val overview: String = "",

    @SerialName("original_language")
    val originalLanguage: String = "",

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

    @SerialName("media_type")
    val mediaType: String = "",

    @SerialName("release_date")
    val releaseDate: String = "",

    @SerialName("vote_average")
    val voteAverage: Double = 0.0,

    @SerialName("id")
    val id: Int = 0,

    @SerialName("adult")
    val adult: Boolean = false,

    @SerialName("vote_count")
    val voteCount: Int = 0,

    @SerialName("first_air_date")
    val firstAirDate: String = "",

    @SerialName("origin_country")
    val originCountry: List<String> = emptyList(),

    @SerialName("original_name")
    val originalName: String = "",

    @SerialName("name")
    val name: String = "",
)
