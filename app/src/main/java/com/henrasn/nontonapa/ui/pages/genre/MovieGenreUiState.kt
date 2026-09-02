package com.henrasn.nontonapa.ui.pages.genre

import com.henrasn.nontonapa.model.uimodel.genre.GenreUiData

data class MovieGenreUiState(
    val genres: List<GenreUiData> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val isRefreshing: Boolean = false,
)