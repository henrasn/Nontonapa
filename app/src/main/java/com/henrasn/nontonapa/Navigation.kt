package com.henrasn.nontonapa

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.henrasn.nontonapa.ui.pages.detail.DetailMovieScreen
import com.henrasn.nontonapa.ui.pages.genre.MovieGenreScreen
import com.henrasn.nontonapa.ui.pages.movie.MoviesScreen
import com.henrasn.nontonapa.ui.pages.reviewlist.ReviewsScreen

@Composable
fun MainNavigation() {
    val backStack = rememberNavBackStack(Genre)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider =
            entryProvider {
                entry<Genre> {
                    MovieGenreScreen(onGenreSelected = { genreId ->
                        backStack.add(Movie(genreId))
                    })
                }

                entry<Movie> { param ->
                    MoviesScreen(param.genreId) { movieId ->
                        backStack.add(DetailMovie(movieId))
                    }
                }

                entry<DetailMovie> { param ->
                    DetailMovieScreen(
                        movieId = param.movieId,
                        onSeeAllReview = {
                            backStack.add(ReviewList(param.movieId))
                        }
                    )
                }

                entry<ReviewList> { param->
                    ReviewsScreen(param.movieId)
                }
            },
    )
}
