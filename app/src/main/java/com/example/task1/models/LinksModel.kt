package com.example.task1.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LinksModel(
    @Expose
    val explorer: List<String> = emptyList(),

    @Expose
    val facebook: List<String> = emptyList(),

    @Expose
    val reddit: List<String> = emptyList(),

    @SerializedName("source_code")
    val sourceCode: List<String> = emptyList(),

    @Expose
    val website: List<String> = emptyList(),

    @Expose
    val youtube: List<String> = emptyList()
)