package com.henrasn.nontonapa

import androidx.compose.runtime.Composable
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.henrasn.nontonapa.ui.pages.genre.MovieGenreScreen

@Composable
fun MainNavigation() {
    val backStack = rememberNavBackStack(Genre)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider =
            entryProvider {
                entry<Genre> {
                    MovieGenreScreen(onGenreSelected = {
                        backStack.add(Movie)
                    })
                }
            },
    )
}
