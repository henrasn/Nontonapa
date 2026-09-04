package com.henrasn.nontonapa.data.local.entity

import androidx.room.Entity

@Entity(tableName = "review_remote_keys", primaryKeys = ["id", "movieId"])
data class ReviewRemoteKeys(
    val id: String,
    val movieId: Int,
    val prevKey: Int?,
    val nextKey: Int?
)
