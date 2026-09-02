package com.henrasn.nontonapa.data.repo

import com.henrasn.nontonapa.model.uimodel.genre.GenreUiData

interface MovieRepository {
    suspend fun getMovieGenres(): Result<List<GenreUiData>>
}