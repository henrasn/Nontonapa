package com.henrasn.nontonapa.data.source

import com.henrasn.nontonapa.model.dto.genre.MovieGenreResponse

interface MovieDataSource {
    suspend fun getMovieGenres(): MovieGenreResponse
}