package com.henrasn.nontonapa.core.error

import com.henrasn.nontonapa.R

fun Throwable.toUiText(): ErrorUiText {
    return (this as? AppException)?.errorUiText
        ?: ErrorUiText.StringResource(R.string.err_unknown)
}