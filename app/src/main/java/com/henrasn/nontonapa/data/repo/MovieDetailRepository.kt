package com.henrasn.nontonapa.data.repo

import com.henrasn.nontonapa.data.model.uimodel.moviedetail.MovieDetailUiData

interface MovieDetailRepository {
    suspend fun getMovieDetail(movieId: Int): Result<MovieDetailUiData>
}
