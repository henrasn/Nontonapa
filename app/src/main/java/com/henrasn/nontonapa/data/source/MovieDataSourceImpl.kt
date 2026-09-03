package com.henrasn.nontonapa.data.source

import com.henrasn.nontonapa.core.network.TmdbApiService
import com.henrasn.nontonapa.data.model.dto.genre.MovieGenreResponse
import javax.inject.Inject

class MovieDataSourceImpl @Inject constructor(val apiService: TmdbApiService) : MovieDataSource {
    override suspend fun getMovieGenres(): MovieGenreResponse {
        return apiService.getMovieGenres()
    }
}

