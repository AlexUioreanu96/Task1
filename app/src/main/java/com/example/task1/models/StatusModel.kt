package com.example.task1.models

import com.google.gson.annotations.SerializedName

data class StatusModel(

    @SerializedName("status_message")
    val statusMessage: String = "",

    @SerializedName("expires_at")
    val expiresAt: String = "",

    @SerializedName("status_code")
    val statusCode: Int = 0,

    @SerializedName("success")
    var success: Boolean = false,

    @SerializedName("failure")
    val failure: Boolean = false,

    @SerializedName("request_token")
    val requestToken: String = "",

    @SerializedName("session_id")
    val sessionId: String = ""
)
