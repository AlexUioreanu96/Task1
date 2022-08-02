package com.example.task1.models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    @field:SerializedName("include_adult")
    val includeAdult: Boolean? = false,

    @field:SerializedName("iso_3166_1")
    val iso31661: String? = "",

    @field:SerializedName("name")
    val name: String? = "",

    @field:SerializedName("avatar")
    val avatar: Avatar? = null,

    @field:SerializedName("id")
    val id: Int? = 0,

    @field:SerializedName("iso_639_1")
    val iso6391: String? = "",

    @SerializedName("password")
    var password: String? = "",

    @SerializedName("request_token")
    var requestToken: String? = "",

    @SerializedName("username")
    var username: String? = ""
)

@Serializable
data class Tmdb(
    @field:SerializedName("avatar_path")
    @Contextual
    val avatarPath: Any? = null
)

@Serializable
data class Gravatar(
    @field:SerializedName("hash")
    val hash: String? = null
)

@Serializable
data class Avatar(
    @field:SerializedName("tmdb")
    val tmdb: Tmdb? = null,
    @field:SerializedName("gravatar")
    val gravatar: Gravatar? = null
)

