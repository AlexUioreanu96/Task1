package com.example.task1.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Tag {
    @SerializedName("coin_counter")
    var coinCounter: Long? = null

    @SerializedName("ico_counter")
    var icoCounter: Long? = null

    @Expose
    var id: String? = null

    @Expose
    var name: String? = null
}