package com.henrasn.nontonapa.data.mapper

import com.henrasn.nontonapa.data.model.dto.genre.MovieGenreResponse
import com.henrasn.nontonapa.data.model.uimodel.genre.GenreUiData

fun MovieGenreResponse.toGenreUiDataList(): List<GenreUiData> {
    return genres.orEmpty().mapNotNull { responseListItem ->
        responseListItem?.let { item ->
            GenreUiData(
                id = item.id,
                genreName = item.name.orEmpty()
            )
        }
    }
}