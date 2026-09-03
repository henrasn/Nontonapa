package com.henrasn.nontonapa.data.repo

import com.henrasn.nontonapa.data.model.uimodel.genre.GenreUiData

interface MovieRepository {
    suspend fun getMovieGenres(): Result<List<GenreUiData>>
}