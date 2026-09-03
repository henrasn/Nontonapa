package com.henrasn.nontonapa.data.local.entity

import androidx.room.Entity

@Entity(tableName = "movies", primaryKeys = ["id", "genreId"])
data class MovieEntity(
    val id: Int,
    val genreId: Int,
    val title: String,
    val backdropPath: String,
    val releaseDate: String,
    val voteAverage: Float,
    val genreIds: String,
    val sortOrder: Int
)
