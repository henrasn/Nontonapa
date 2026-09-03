package com.henrasn.nontonapa.data.repo

import com.henrasn.nontonapa.core.di.IoDispatcher
import com.henrasn.nontonapa.core.network.safeApiCall
import com.henrasn.nontonapa.data.mapper.toMovieDetailUiData
import com.henrasn.nontonapa.data.mapper.toMovieReviewUiDataList
import com.henrasn.nontonapa.data.model.uimodel.moviedetail.MovieDetailUiData
import com.henrasn.nontonapa.data.model.uimodel.moviereview.MovieReviewUiData
import com.henrasn.nontonapa.data.source.MovieDetailDataSource
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MovieDetailRepositoryImpl @Inject constructor(
    val movieDetailDataSource: MovieDetailDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : MovieDetailRepository {
    override suspend fun getMovieDetail(movieId: Int): Result<MovieDetailUiData> =
        safeApiCall(dispatcher) {
            movieDetailDataSource.getMovieDetail(movieId).toMovieDetailUiData()
        }

    override suspend fun getMovieReviews(movieId: Int): Result<List<MovieReviewUiData>> =
        safeApiCall(dispatcher) {
            movieDetailDataSource.getMovieReviews(movieId).toMovieReviewUiDataList()
        }
}
