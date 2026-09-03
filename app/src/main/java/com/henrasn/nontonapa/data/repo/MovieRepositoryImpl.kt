package com.henrasn.nontonapa.data.repo

import com.henrasn.nontonapa.core.di.IoDispatcher
import com.henrasn.nontonapa.core.network.safeApiCall
import com.henrasn.nontonapa.data.mapper.toGenreUiDataList
import com.henrasn.nontonapa.data.model.uimodel.genre.GenreUiData
import com.henrasn.nontonapa.data.source.MovieDataSource
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    val movieDataSource: MovieDataSource,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : MovieRepository {
    override suspend fun getMovieGenres(): Result<List<GenreUiData>> = safeApiCall(dispatcher) {
        movieDataSource.getMovieGenres().toGenreUiDataList()
    }
}