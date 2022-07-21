package com.example.task1.models

import com.google.gson.annotations.Expose


data class LinksExtended(
    @Expose
    var type: String? = "",
    @Expose
    var url: String? = "",

    var stats: StatsModel

)