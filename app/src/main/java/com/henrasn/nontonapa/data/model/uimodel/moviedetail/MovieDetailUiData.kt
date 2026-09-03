package com.henrasn.nontonapa.data.model.uimodel.moviedetail

data class MovieDetailUiData(
    val id: Int,
    val voteAverage: Float,
    val backdropPath: String,
    val title: String,
    val releaseDate: String,
    val overview: String,
    val tagline: String
)
