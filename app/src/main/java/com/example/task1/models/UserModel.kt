package com.example.task1.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    @SerializedName("include_adult")
    val includeAdult: Boolean = false,

    @SerializedName("iso_3166_1")
    val iso31661: String = "",

    @SerializedName("name")
    val name: String = "",

    @SerializedName("avatar")
    val avatar: Avatar? = null,

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("iso_639_1")
    val iso6391: String = "",

    @SerializedName("password")
    var password: String = "",

    @SerializedName("request_token")
    var requestToken: String = "",

    @SerializedName("username")
    var username: String = ""
)

@Serializable
data class Tmdb(
    @SerializedName("avatar_path")
    @Contextual
    val avatarPath: Any? = null
)

@Serializable
data class Gravatar(
    @SerializedName("hash")
    val hash: String = ""
)

@Serializable
data class Avatar(
    @SerializedName("tmdb")
    val tmdb: Tmdb? = null,
    @SerializedName("gravatar")
    val gravatar: Gravatar? = null
)

