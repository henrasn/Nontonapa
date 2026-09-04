package com.henrasn.nontonapa.data.model.dto.moviereview

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieReviewResponse(
    @SerialName("page")
    val page: Int = 0,

    @SerialName("results")
    val results: List<ReviewItem?>? = null,

    @SerialName("total_pages")
    val totalPages: Int = 0,

    @SerialName("total_results")
    val totalResults: Int = 0
)

@Serializable
data class ReviewItem(
    @SerialName("author")
    val author: String? = null,

    @SerialName("author_details")
    val authorDetails: AuthorDetails? = null,

    @SerialName("content")
    val content: String? = null,

    @SerialName("created_at")
    val createdAt: String? = null,

    @SerialName("id")
    val id: String? = null,

    @SerialName("url")
    val url: String? = null
)

@Serializable
data class AuthorDetails(
    @SerialName("name")
    val name: String? = null,

    @SerialName("username")
    val username: String? = null,

    @SerialName("avatar_path")
    val avatarPath: String? = null,

    @SerialName("rating")
    val rating: Float? = null
)
