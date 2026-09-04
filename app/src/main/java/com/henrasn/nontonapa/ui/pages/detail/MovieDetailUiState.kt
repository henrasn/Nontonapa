package com.henrasn.nontonapa.ui.pages.detail

import com.henrasn.nontonapa.data.model.uimodel.moviedetail.MovieDetailUiData
import com.henrasn.nontonapa.data.model.uimodel.moviereview.MovieReviewUiData

data class MovieDetailUiState(
    val movie: MovieDetailUiData? = null,
    val reviews: List<MovieReviewUiData> = emptyList(),
    val isLoading: Boolean = false,
    val isReviewLoading: Boolean = false,
)
