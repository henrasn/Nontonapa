package com.henrasn.nontonapa.ui.pages.detail

sealed interface MovieDetailIntent {
    data class LoadMovieDetail(val movieId: Int) : MovieDetailIntent
    data class LoadMovieReviews(val movieId: Int) : MovieDetailIntent
}
