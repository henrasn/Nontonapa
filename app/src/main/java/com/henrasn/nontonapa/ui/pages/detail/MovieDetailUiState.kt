package com.henrasn.nontonapa.ui.pages.detail

import com.henrasn.nontonapa.data.model.uimodel.moviedetail.MovieDetailUiData
import com.henrasn.nontonapa.data.model.uimodel.moviereview.MovieReviewUiData

data class MovieDetailUiState(
    val movie: MovieDetailUiData? = null,
    val isLoading: Boolean = false,
    val reviews: List<MovieReviewUiData> = emptyList(),
    val isReviewLoading: Boolean = false,
)
