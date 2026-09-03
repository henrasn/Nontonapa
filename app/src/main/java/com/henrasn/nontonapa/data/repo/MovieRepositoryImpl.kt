package com.henrasn.nontonapa.data.repo

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map as pagingMap
import com.henrasn.nontonapa.core.di.IoDispatcher
import com.henrasn.nontonapa.core.network.safeApiCall
import com.henrasn.nontonapa.data.local.MovieDatabase
import com.henrasn.nontonapa.data.local.dao.MovieDao
import com.henrasn.nontonapa.data.mapper.toGenreUiDataList
import com.henrasn.nontonapa.data.mapper.toMovieUiData
import com.henrasn.nontonapa.data.model.uimodel.genre.GenreUiData
import com.henrasn.nontonapa.data.model.uimodel.movie.MovieUiData
import com.henrasn.nontonapa.data.paging.MovieRemoteMediator
import com.henrasn.nontonapa.data.source.MovieDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    val movieDataSource: MovieDataSource,
    private val movieDao: MovieDao,
    private val movieDatabase: MovieDatabase,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : MovieRepository {
    override suspend fun getMovieGenres(): Result<List<GenreUiData>> = safeApiCall(dispatcher) {
        movieDataSource.getMovieGenres().toGenreUiDataList()
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getDiscoverMovies(genreId: Int): Flow<PagingData<MovieUiData>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            remoteMediator = MovieRemoteMediator(genreId, movieDataSource, movieDatabase, dispatcher),
            pagingSourceFactory = { movieDao.getMoviesPagingSource(genreId) }
        ).flow.map { pagingData ->
            pagingData.pagingMap { entity -> entity.toMovieUiData() }
        }
    }
}