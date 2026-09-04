package com.henrasn.nontonapa.data.repo

import androidx.paging.PagingData
import com.henrasn.nontonapa.data.model.uimodel.moviereview.MovieReviewUiData
import kotlinx.coroutines.flow.Flow

interface ReviewRepository {
    suspend fun getMovieReviews(movieId: Int): Result<List<MovieReviewUiData>>
    fun getMovieReviewsPaged(movieId: Int): Flow<PagingData<MovieReviewUiData>>
}
