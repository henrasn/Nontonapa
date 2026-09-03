package com.henrasn.nontonapa.ui.pages.detail

import com.henrasn.nontonapa.core.error.ErrorUiText

sealed interface MovieDetailEffect {
    data class ShowError(val message: ErrorUiText) : MovieDetailEffect
}
