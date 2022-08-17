package com.example.task1.models

import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    @SerialName("include_adult")
    val includeAdult: Boolean? = false,

    @SerialName("iso_3166_1")
    val iso31661: String? = "",

    @SerialName("name")
    val name: String? = "",

    @SerialName("avatar")
    val avatar: Avatar? = null,

    @SerialName("id")
    val id: Int? = 0,

    @SerialName("iso_639_1")
    val iso6391: String? = ""
)

@Serializable
data class LoginRequest(
    @SerialName("password")
    val password: String,

    @SerialName("request_token")
    var requestToken: String,

    @SerialName("username")
    val username: String
)

@Serializable
data class Tmdb(
    @SerialName("avatar_path")
    @Contextual
    val avatarPath: Any? = null
)

@Serializable
data class Gravatar(
    @SerialName("hash")
    val hash: String? = null
)

@Serializable
data class Avatar(
    @SerialName("tmdb")
    val tmdb: Tmdb? = null,
    @SerialName("gravatar")
    val gravatar: Gravatar? = null
)

