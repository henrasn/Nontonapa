package com.henrasn.nontonapa.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.henrasn.nontonapa.core.di.IoDispatcher
import com.henrasn.nontonapa.data.local.dao.MovieDao
import com.henrasn.nontonapa.data.local.entity.MovieEntity
import com.henrasn.nontonapa.data.mapper.toMovieEntity
import com.henrasn.nontonapa.data.model.dto.movie.MovieDiscoverResponse
import com.henrasn.nontonapa.data.source.MovieDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val genreId: Int,
    private val movieDataSource: MovieDataSource,
    private val movieDao: MovieDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : RemoteMediator<Int, MovieEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                state.lastItemOrNull()?.let { lastItem ->
                    (lastItem.sortOrder / state.config.pageSize) + 2
                } ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }

        return try {
            val response: MovieDiscoverResponse = withContext(dispatcher) {
                movieDataSource.getDiscoverMovies(page, genreId)
            }

            if (response.results.isNullOrEmpty()) {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            withContext(dispatcher) {
                if (loadType == LoadType.REFRESH) {
                    movieDao.clearAll()
                }
                val startOffset = state.pages.sumOf { it.data.size }
                val entities = response.results.mapIndexed { index, item ->
                    item?.toMovieEntity(sortOrder = startOffset + index)
                }.filterNotNull()

                movieDao.insertAll(entities)
            }

            MediatorResult.Success(
                endOfPaginationReached = page >= response.totalPages
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }
}
