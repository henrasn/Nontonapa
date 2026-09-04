package com.henrasn.nontonapa.ui.pages.reviewlist

sealed interface ReviewListIntent {
    data class LoadReviews(val movieId: Int) : ReviewListIntent
}
