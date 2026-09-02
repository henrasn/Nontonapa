package com.henrasn.nontonapa.ui.pages.genre

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.henrasn.nontonapa.R
import com.henrasn.nontonapa.model.uimodel.genre.GenreUiData
import com.henrasn.nontonapa.ui.component.GenreItem
import com.henrasn.nontonapa.ui.theme.NontonTheme

@Composable
fun MovieGenreScreen(
    onGenreSelected: (Int) -> Unit,
    viewModel: MovieGenreViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onIntent(MovieGenreIntent.loadMovieGenres)
    }

    MovieGenreContent(
        uiState = uiState,
        onIntent = viewModel::onIntent,
        onGenreSelected = onGenreSelected
    )

}

@Composable
fun MovieGenreContent(
    uiState: MovieGenreUiState,
    onIntent: (MovieGenreIntent) -> Unit,
    onGenreSelected: (Int) -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = uiState.isRefreshing,
        onRefresh = {
            onIntent(MovieGenreIntent.refreshPage)
        },
        state = pullToRefreshState
    ) {
        Box(contentAlignment = Alignment.Center) {
            if (uiState.genres.isEmpty()) {
                Image(
                    painter = painterResource(id = R.drawable.img_empty_placeholder),
                    contentDescription = null
                )
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.genres, key = { genre -> genre.id }) { genre ->
                        GenreItem(
                            genreName = genre.genreName,
                            onClick = { onGenreSelected(genre.id) }
                        )
                    }
                }
            }

            if (uiState.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewNonEmptyMovieGenreContent() {
    NontonTheme {
        MovieGenreContent(
            MovieGenreUiState(
                genres = listOf(
                    GenreUiData(1, "Sci-Fi"),
                    GenreUiData(2, "Documentary"),
                    GenreUiData(3, "Action"),
                )
            ),
            onIntent = {},
            onGenreSelected = {}
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewEmptyMovieGenreContent() {
    NontonTheme {
        MovieGenreContent(
            MovieGenreUiState(),
            onIntent = {},
            onGenreSelected = {}
        )
    }
}