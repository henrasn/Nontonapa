package com.henrasn.nontonapa.data.repo

import com.henrasn.nontonapa.data.model.uimodel.moviedetail.MovieDetailUiData
import com.henrasn.nontonapa.data.model.uimodel.moviereview.MovieReviewUiData

interface MovieDetailRepository {
    suspend fun getMovieDetail(movieId: Int): Result<MovieDetailUiData>
    suspend fun getMovieReviews(movieId: Int): Result<List<MovieReviewUiData>>
}
