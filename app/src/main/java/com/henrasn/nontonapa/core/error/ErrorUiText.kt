package com.henrasn.nontonapa.core.error

import android.content.Context
import androidx.annotation.StringRes

sealed interface ErrorUiText {
    data class DynamicString(val value: String) : ErrorUiText
    data class StringResource(@StringRes val resId: Int) : ErrorUiText

    fun asString(context: Context): String {
        return when (this) {
            is DynamicString -> value
            is StringResource -> context.getString(resId)
        }
    }
}