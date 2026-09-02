package com.henrasn.nontonapa.ui.pages.genre

sealed interface MovieGenreIntent {
    object loadMovieGenres : MovieGenreIntent
    object refreshPage : MovieGenreIntent
}