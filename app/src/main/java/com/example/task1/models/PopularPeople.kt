package com.example.task1.models

import com.google.gson.annotations.SerializedName

data class PopularPeople(

    @SerializedName("page")
    val page: Int = 0,

    @SerializedName("total_pages")
    val totalPages: Int = 0,

    @SerializedName("results")
    val results: List<People> = emptyList(),

    @SerializedName("total_results")
    val totalResults: Int = 0
)

data class Star(val name: String = "", val profilePath: String = "")

data class People(

    @SerializedName("gender")
    val gender: Int = 0,

    @SerializedName("known_for_department")
    val knownForDepartment: String = "",

    @SerializedName("known_for")
    val knownFor: List<KnownForItem> = emptyList(),

    @SerializedName("popularity")
    val popularity: Double = 0.0,

    @SerializedName("name")
    val name: String = "",

    @SerializedName("profile_path")
    val profilePath: String = "",

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("adult")
    val adult: Boolean = false
)

data class KnownForItem(

    @SerializedName("overview")
    val overview: String = "",

    @SerializedName("original_language")
    val originalLanguage: String = "",

    @SerializedName("original_title")
    val originalTitle: String = "",

    @SerializedName("video")
    val video: Boolean = false,

    @SerializedName("title")
    val title: String = "",

    @SerializedName("genre_ids")
    val genreIds: List<Int> = emptyList(),

    @SerializedName("poster_path")
    val posterPath: String = "",

    @SerializedName("backdrop_path")
    val backdropPath: String = "",

    @SerializedName("media_type")
    val mediaType: String = "",

    @SerializedName("release_date")
    val releaseDate: String = "",

    @SerializedName("vote_average")
    val voteAverage: Double? = 0.0,

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("adult")
    val adult: Boolean = false,

    @SerializedName("vote_count")
    val voteCount: Int = 0,

    @SerializedName("first_air_date")
    val firstAirDate: String = "",

    @SerializedName("origin_country")
    val originCountry: List<String> = emptyList(),

    @SerializedName("original_name")
    val originalName: String? = "",

    @SerializedName("name")
    val name: String = ""
)
