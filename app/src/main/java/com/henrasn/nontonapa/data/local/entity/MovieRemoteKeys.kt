package com.henrasn.nontonapa.data.local.entity

import androidx.room.Entity

@Entity(tableName = "movie_remote_keys", primaryKeys = ["movieId", "genreId"])
data class MovieRemoteKeys(
    val movieId: Int,
    val genreId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
