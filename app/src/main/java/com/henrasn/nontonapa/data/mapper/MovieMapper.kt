package com.henrasn.nontonapa.data.mapper

import com.henrasn.nontonapa.data.local.entity.MovieEntity
import com.henrasn.nontonapa.data.model.dto.movie.MovieItem
import com.henrasn.nontonapa.data.model.uimodel.movie.MovieUiData

fun MovieItem.toMovieEntity(sortOrder: Int): MovieEntity {
    return MovieEntity(
        id = id,
        title = title.orEmpty(),
        backdropPath = backdropPath.orEmpty(),
        releaseDate = releaseDate.orEmpty(),
        voteAverage = voteAverage ?: 0f,
        genreIds = genreIds.orEmpty().joinToString(","),
        sortOrder = sortOrder
    )
}

fun MovieEntity.toMovieUiData(): MovieUiData {
    return MovieUiData(
        id = id,
        title = title,
        backdropPath = backdropPath,
        releaseDate = releaseDate,
        voteAverage = voteAverage
    )
}
