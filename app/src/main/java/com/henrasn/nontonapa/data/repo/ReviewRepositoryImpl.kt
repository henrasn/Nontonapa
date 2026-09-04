package com.henrasn.nontonapa.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map as pagingMap
import com.henrasn.nontonapa.core.di.IoDispatcher
import com.henrasn.nontonapa.core.network.safeApiCall
import com.henrasn.nontonapa.data.local.MovieDatabase
import com.henrasn.nontonapa.data.local.dao.ReviewDao
import com.henrasn.nontonapa.data.local.dao.ReviewRemoteKeysDao
import com.henrasn.nontonapa.data.mapper.toMovieReviewUiData
import com.henrasn.nontonapa.data.mapper.toMovieReviewUiDataList
import com.henrasn.nontonapa.data.model.uimodel.moviereview.MovieReviewUiData
import com.henrasn.nontonapa.data.paging.ReviewRemoteMediator
import com.henrasn.nontonapa.data.source.MovieDetailDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReviewRepositoryImpl @Inject constructor(
    val movieDetailDataSource: MovieDetailDataSource,
    private val reviewDao: ReviewDao,
    private val reviewRemoteKeysDao: ReviewRemoteKeysDao,
    private val movieDatabase: MovieDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : ReviewRepository {
    override suspend fun getMovieReviews(movieId: Int): Result<List<MovieReviewUiData>> =
        safeApiCall(dispatcher) {
            movieDetailDataSource.getMovieReviews(movieId, page = 1).toMovieReviewUiDataList()
        }

    @OptIn(ExperimentalPagingApi::class)
    override fun getMovieReviewsPaged(movieId: Int): Flow<PagingData<MovieReviewUiData>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            remoteMediator = ReviewRemoteMediator(
                movieId = movieId,
                movieDetailDataSource = movieDetailDataSource,
                movieDatabase = movieDatabase,
                dispatcher = dispatcher
            ),
            pagingSourceFactory = { reviewDao.getReviewsPagingSource(movieId) }
        ).flow.map { pagingData ->
            pagingData.pagingMap { entity -> entity.toMovieReviewUiData() }
        }
    }
}
