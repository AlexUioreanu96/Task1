package com.example.task1.models

import com.google.gson.annotations.Expose


data class TeamModel(
    @Expose
    val id: String = "",

    @Expose
    val name: String = "",

    @Expose
    val position: String = ""
)