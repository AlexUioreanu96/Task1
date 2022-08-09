package com.example.task1.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TABLE_NAME)
data class MovieEntity(
    @PrimaryKey
    val id: Int?,
    val image: String?,
    val voteAvg: Double?,
    var isFavorite: Boolean? = false
)