package com.henrasn.nontonapa.data.source

import com.henrasn.nontonapa.data.model.dto.moviedetail.MovieDetailResponse

interface MovieDetailDataSource {
    suspend fun getMovieDetail(movieId: Int): MovieDetailResponse
}
