package com.example.task1.models

import com.google.gson.annotations.Expose


data class LinksExtended(
    @Expose
    val type: String = "",
    @Expose
    val url: String = "",

    val stats: StatsModel = StatsModel()

)