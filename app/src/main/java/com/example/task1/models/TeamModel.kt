package com.example.task1.models

import com.google.gson.annotations.Expose


data class TeamModel(
    @Expose
    var id: String = "",

    @Expose
    var name: String = "",

    @Expose
    var position: String = ""
)