package com.henrasn.nontonapa.data.source

import com.henrasn.nontonapa.data.model.dto.moviedetail.MovieDetailResponse
import com.henrasn.nontonapa.data.model.dto.moviereview.MovieReviewResponse

interface MovieDetailDataSource {
    suspend fun getMovieDetail(movieId: Int): MovieDetailResponse
    suspend fun getMovieReviews(movieId: Int, page: Int): MovieReviewResponse
}
