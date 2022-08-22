package com.example.task1.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = LOGIN_MOVIE)
data class StatusModel(
    @PrimaryKey
    val id: Int = 1,

    @SerialName("status_message")
    val statusMessage: String? = "",

    @SerialName("expires_at")
    val expiresAt: String? = "",

    @SerialName("status_code")
    val statusCode: Int? = 0,

    @SerialName("success")
    var success: Boolean? = false,

    @SerialName("failure")
    val failure: Boolean? = false,

    @SerialName("request_token")
    var requestToken: String = "",

    @SerialName("session_id")
    var sessionId: String? = ""
)
