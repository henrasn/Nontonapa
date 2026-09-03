package com.henrasn.nontonapa.data.source

import com.henrasn.nontonapa.core.network.TmdbApiService
import com.henrasn.nontonapa.data.model.dto.moviedetail.MovieDetailResponse
import javax.inject.Inject

class MovieDetailDataSourceImpl @Inject constructor(
    val apiService: TmdbApiService
) : MovieDetailDataSource {
    override suspend fun getMovieDetail(movieId: Int): MovieDetailResponse {
        return apiService.getMovieDetail(movieId)
    }
}
