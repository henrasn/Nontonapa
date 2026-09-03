package com.henrasn.nontonapa.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val backdropPath: String,
    val releaseDate: String,
    val voteAverage: Float,
    val genreIds: String,
    val sortOrder: Int
)
