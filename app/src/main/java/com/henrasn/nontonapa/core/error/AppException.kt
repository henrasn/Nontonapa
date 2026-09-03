package com.henrasn.nontonapa.core.error

class AppException(val errorUiText: ErrorUiText, cause: Throwable? = null) : Exception(cause)