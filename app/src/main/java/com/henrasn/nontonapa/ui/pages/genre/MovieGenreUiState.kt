package com.henrasn.nontonapa.ui.pages.genre

import com.henrasn.nontonapa.data.model.uimodel.genre.GenreUiData

data class MovieGenreUiState(
    val genres: List<GenreUiData> = emptyList(),
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
)