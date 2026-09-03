package com.henrasn.nontonapa.data.model.dto.moviedetail

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MovieDetailResponse(
    @SerialName("id")
    val id: Int,

    @SerialName("vote_average")
    val voteAverage: Float? = null,

    @SerialName("backdrop_path")
    val backdropPath: String? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("release_date")
    val releaseDate: String? = null,

    @SerialName("overview")
    val overview: String? = null,

    @SerialName("tagline")
    val tagline: String? = null
)
