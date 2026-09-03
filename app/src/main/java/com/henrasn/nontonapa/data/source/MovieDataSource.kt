package com.henrasn.nontonapa.data.source

import com.henrasn.nontonapa.data.model.dto.genre.MovieGenreResponse
import com.henrasn.nontonapa.data.model.dto.movie.MovieDiscoverResponse

interface MovieDataSource {
    suspend fun getMovieGenres(): MovieGenreResponse
    suspend fun getDiscoverMovies(page: Int, genreId: Int): MovieDiscoverResponse
}