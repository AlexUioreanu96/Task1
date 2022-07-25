package com.example.task1.models

import com.google.gson.annotations.Expose

data class WhitepaperModel(
    @Expose
    val link: String = "",

    @Expose
    val thumbnail: String = ""
)