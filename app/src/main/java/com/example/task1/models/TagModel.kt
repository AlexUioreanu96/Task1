package com.example.task1.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TagModel(
    @SerializedName("coin_counter")
    var coinCounter: Long = 0,

    @SerializedName("ico_counter")
    var icoCounter: Long = 0,

    @Expose
    var id: String = "",

    @Expose
    var name: String = "",
)