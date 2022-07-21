package com.example.task1

import com.google.gson.annotations.Expose


data class LinksExtended(
    @Expose
    var type: String? = null,
    @Expose var url: String? = null
)