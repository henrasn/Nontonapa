package com.henrasn.nontonapa.data.mapper

import com.henrasn.nontonapa.data.model.dto.moviedetail.MovieDetailResponse
import com.henrasn.nontonapa.data.model.uimodel.moviedetail.MovieDetailUiData

fun MovieDetailResponse.toMovieDetailUiData(): MovieDetailUiData {
    return MovieDetailUiData(
        id = id,
        voteAverage = voteAverage ?: 0f,
        backdropPath = backdropPath.orEmpty(),
        title = title.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        overview = overview.orEmpty(),
        tagline = tagline.orEmpty()
    )
}
