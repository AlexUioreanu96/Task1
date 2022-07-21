package com.example.task1.models

import com.google.gson.annotations.Expose

data class WhitepaperModel(
    @Expose
    var link: String = "",

    @Expose
    var thumbnail: String = ""
)