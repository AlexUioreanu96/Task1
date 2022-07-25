package com.example.task1.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TagModel(
    @SerializedName("coin_counter")
    val coinCounter: Long = 0,

    @SerializedName("ico_counter")
    val icoCounter: Long = 0,

    @Expose
    val id: String = "",

    @Expose
    val name: String = "",
)