package com.example.task1.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PopularPeople(

    @SerialName("page")
    val page: Int? = null,

    @SerialName("total_pages")
    val totalPages: Int? = null,

    @SerialName("results")
    val results: List<People>,

    @SerialName("total_results")
    val totalResults: Int? = null
)

@Serializable
data class Star(val name: String?, val profilePath: String?)

@Serializable
data class People(

    @SerialName("gender")
    val gender: Int? = null,

    @SerialName("known_for_department")
    val knownForDepartment: String? = null,

    @SerialName("known_for")
    val knownFor: List<KnownForItem?>? = null,

    @SerialName("popularity")
    val popularity: Double? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("profile_path")
    val profilePath: String? = null,

    @SerialName("id")
    val id: Int? = null,

    @SerialName("adult")
    val adult: Boolean? = null
)

@Serializable
data class KnownForItem(

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("original_language")
    val originalLanguage: String? = null,

    @SerialName("original_title")
    val originalTitle: String? = null,

    @SerialName("video")
    val video: Boolean? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("genre_ids")
    val genreIds: List<Int?>? = null,

    @SerialName("poster_path")
    val posterPath: String? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("media_type")
    val mediaType: String? = null,

    @SerialName("release_date")
    val releaseDate: String? = null,

    @SerialName("vote_average")
    val voteAverage: Double? = null,

    @SerialName("id")
    val id: Int? = null,

    @SerialName("adult")
    val adult: Boolean? = null,

    @SerialName("vote_count")
    val voteCount: Int? = null,

    @SerialName("first_air_date")
    val firstAirDate: String? = null,

    @SerialName("origin_country")
    val originCountry: List<String?>? = null,

    @SerialName("original_name")
    val originalName: String? = null,

    @SerialName("name")
    val name: String? = null
)
