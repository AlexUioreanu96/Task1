package com.example.task1

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Links {
    @Expose
    var explorer: List<String>? = null

    @Expose
    var facebook: List<String>? = null

    @Expose
    var reddit: List<String>? = null

    @SerializedName("source_code")
    var sourceCode: List<String>? = null

    @Expose
    var website: List<String>? = null

    @Expose
    var youtube: List<String>? = null
}