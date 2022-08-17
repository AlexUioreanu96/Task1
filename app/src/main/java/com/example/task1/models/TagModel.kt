package com.example.task1.models

import com.google.gson.annotations.Expose
import kotlinx.serialization.SerialName

data class TagModel(
    @SerialName("coin_counter")
    val coinCounter: Long = 0,

    @SerialName("ico_counter")
    val icoCounter: Long = 0,

    @Expose
    val id: String = "",

    @Expose
    val name: String = "",
)