package com.henrasn.nontonapa.ui.pages.detail

import com.henrasn.nontonapa.data.model.uimodel.moviedetail.MovieDetailUiData

data class MovieDetailUiState(
    val movie: MovieDetailUiData? = null,
    val isLoading: Boolean = false,
)
