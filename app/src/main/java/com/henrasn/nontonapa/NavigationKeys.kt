package com.henrasn.nontonapa

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object Genre : NavKey

@Serializable
data class Movie(val genreId:Int) : NavKey
@Serializable
data class DetailMovie(val movieId:Int) : NavKey
