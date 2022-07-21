package com.example.task1.models

import com.google.gson.annotations.Expose


class Team {
    @Expose
    var id: String? = null

    @Expose
    var name: String? = null

    @Expose
    var position: String? = null
}