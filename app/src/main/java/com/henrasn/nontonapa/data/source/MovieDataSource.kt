package com.henrasn.nontonapa.data.source

import com.henrasn.nontonapa.data.model.dto.genre.MovieGenreResponse

interface MovieDataSource {
    suspend fun getMovieGenres(): MovieGenreResponse
}