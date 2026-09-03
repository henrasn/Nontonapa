package com.henrasn.nontonapa.core.di

import javax.inject.Qualifier

// DispatcherModule.kt
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher

