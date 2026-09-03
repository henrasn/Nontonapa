package com.henrasn.nontonapa.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.henrasn.nontonapa.core.di.IoDispatcher
import com.henrasn.nontonapa.data.local.MovieDatabase
import com.henrasn.nontonapa.data.local.entity.MovieEntity
import com.henrasn.nontonapa.data.local.entity.MovieRemoteKeys
import com.henrasn.nontonapa.data.mapper.toMovieEntity
import com.henrasn.nontonapa.data.model.dto.movie.MovieItem
import com.henrasn.nontonapa.data.source.MovieDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

@OptIn(ExperimentalPagingApi::class)
class MovieRemoteMediator(
    private val genreId: Int,
    private val movieDataSource: MovieDataSource,
    private val movieDatabase: MovieDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : RemoteMediator<Int, MovieEntity>() {

    private val movieDao = movieDatabase.movieDao()
    private val remoteKeysDao = movieDatabase.movieRemoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, MovieEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                val remoteKeys = withContext(dispatcher) {
                    remoteKeysDao.getRemoteKeys(movieId = lastItem.id, genreId = genreId)
                }
                remoteKeys?.nextKey
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }

        return try {
            val response = withContext(dispatcher) {
                movieDataSource.getDiscoverMovies(page, genreId)
            }
            val movies = response.results.orEmpty().filterNotNull()
            val endOfPagination = movies.isEmpty() || page >= response.totalPages

            movieDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    movieDao.clearByGenre(genreId)
                    remoteKeysDao.clearByGenre(genreId)
                }

                val prevKey = if (page == 1) null else page - 1
                val nextKey = if (endOfPagination) null else page + 1

                val startOffset = state.pages.sumOf { it.data.size }
                val entities = movies.mapIndexed { index, item: MovieItem ->
                    item.toMovieEntity(sortOrder = startOffset + index, genreId = genreId)
                }
                val keys = movies.map { item: MovieItem ->
                    MovieRemoteKeys(
                        movieId = item.id,
                        genreId = genreId,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }

                movieDao.insertAll(entities)
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
