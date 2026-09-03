package com.henrasn.nontonapa.ui.pages.genre

import com.henrasn.nontonapa.core.error.ErrorUiText

sealed interface MovieGenreEffect {
    data class ShowError(val message: ErrorUiText) : MovieGenreEffect
}
