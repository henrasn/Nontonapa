package com.henrasn.nontonapa.ui.pages.reviewlist

import com.henrasn.nontonapa.core.error.ErrorUiText

sealed interface ReviewListEffect {
    data class ShowError(val message: ErrorUiText) : ReviewListEffect
}
