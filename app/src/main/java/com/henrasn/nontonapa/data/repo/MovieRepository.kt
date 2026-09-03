package com.henrasn.nontonapa.data.repo

import androidx.paging.PagingData
import com.henrasn.nontonapa.data.model.uimodel.genre.GenreUiData
import com.henrasn.nontonapa.data.model.uimodel.movie.MovieUiData
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovieGenres(): Result<List<GenreUiData>>
    fun getDiscoverMovies(genreId: Int): Flow<PagingData<MovieUiData>>
}