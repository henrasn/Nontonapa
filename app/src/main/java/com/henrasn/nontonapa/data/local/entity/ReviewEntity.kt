package com.henrasn.nontonapa.data.local.entity

import androidx.room.Entity

@Entity(tableName = "reviews", primaryKeys = ["id", "movieId"])
data class ReviewEntity(
    val id: String,
    val movieId: Int,
    val name: String,
    val review: String,
    val date: String,
    val rating: Float,
    val sortOrder: Int
)
