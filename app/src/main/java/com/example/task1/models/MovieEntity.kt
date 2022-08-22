package com.example.task1.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TABLE_MOVIE)
data class MovieEntity(
    @PrimaryKey
    val id: Int = 0,
    val name: String = "",
    val image: String = "",
    val voteAvg: Double = 0.0,
    var isFavorite: Boolean? = false,
    var trending: Int? = 0
)