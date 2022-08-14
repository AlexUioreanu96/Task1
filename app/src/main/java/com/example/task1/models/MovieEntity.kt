package com.example.task1.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TABLE_NAME)
data class MovieEntity(
    @PrimaryKey
    val id: Int?,
    val name: String?,
    val image: String?,
    val voteAvg: Double?,
    var isFavorite: Boolean? = false,
    //TODO: see Robert's case
    val trending: Int? = 0
)