package com.henrasn.nontonapa.ui.common

import com.henrasn.nontonapa.core.error.ErrorUiText

sealed interface CommonEffect {
    data class ShowError(val message: ErrorUiText) : CommonEffect
}