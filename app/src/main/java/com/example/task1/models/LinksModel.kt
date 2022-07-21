package com.example.task1.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LinksModel(
    @Expose
    var explorer: List<String>? = emptyList(),

    @Expose
    var facebook: List<String>? = emptyList(),

    @Expose
    var reddit: List<String>? = emptyList(),

    @SerializedName("source_code")
    var sourceCode: List<String>? = emptyList(),

    @Expose
    var website: List<String>? = emptyList(),

    @Expose
    var youtube: List<String>? = emptyList()
)