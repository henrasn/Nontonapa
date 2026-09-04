package com.henrasn.nontonapa.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.henrasn.nontonapa.core.di.IoDispatcher
import com.henrasn.nontonapa.data.local.MovieDatabase
import com.henrasn.nontonapa.data.local.entity.ReviewEntity
import com.henrasn.nontonapa.data.local.entity.ReviewRemoteKeys
import com.henrasn.nontonapa.data.mapper.toReviewEntity
import com.henrasn.nontonapa.data.model.dto.moviereview.ReviewItem
import com.henrasn.nontonapa.data.source.MovieDetailDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

@OptIn(ExperimentalPagingApi::class)
class ReviewRemoteMediator(
    private val movieId: Int,
    private val movieDetailDataSource: MovieDetailDataSource,
    private val movieDatabase: MovieDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : RemoteMediator<Int, ReviewEntity>() {

    private val reviewDao = movieDatabase.reviewDao()
    private val remoteKeysDao = movieDatabase.reviewRemoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ReviewEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                val remoteKeys = withContext(dispatcher) {
                    remoteKeysDao.getRemoteKeys(id = lastItem.id, movieId = movieId)
                }
                remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }

        return try {
            val response = withContext(dispatcher) {
                movieDetailDataSource.getMovieReviews(movieId = movieId, page = page)
            }
            val reviews = response.results.orEmpty().filterNotNull()
            val endOfPagination = reviews.isEmpty() || page >= response.totalPages

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    reviewDao.clearByMovie(movieId)
                    remoteKeysDao.clearByMovie(movieId)
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPagination) null else page + 1

                val startOffset = state.pages.sumOf { it.data.size }
                val entities = reviews.mapIndexed { index, item: ReviewItem ->
                    item.toReviewEntity(movieId = movieId, sortOrder = startOffset + index)
                }
                val keys = reviews.map { item: ReviewItem ->
                    ReviewRemoteKeys(
                        id = item.id.orEmpty(),
                        movieId = movieId,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }

                reviewDao.insertAll(entities)
                remoteKeysDao.insertAll(keys)
            }

            MediatorResult.Success(endOfPaginationReached = endOfPagination)
        } catch (e: CancellationException) {
            throw e
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
