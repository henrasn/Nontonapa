package com.henrasn.nontonapa.ui.pages.genre

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.henrasn.nontonapa.R
import com.henrasn.nontonapa.data.model.uimodel.genre.GenreUiData
import com.henrasn.nontonapa.ui.component.GenreItem
import com.henrasn.nontonapa.ui.theme.NontonTheme

@Composable
fun MovieGenreScreen(
    onGenreSelected: (Int) -> Unit,
    viewModel: MovieGenreViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.onIntent(MovieGenreIntent.loadMovieGenres)
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MovieGenreEffect.ShowError -> {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(effect.message.asString(context))
                }
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        }
    ) { innerPadding ->
        MovieGenreContent(
            modifier = Modifier.padding(innerPadding),
            uiState = uiState,
            onIntent = viewModel::onIntent,
            onGenreSelected = onGenreSelected
        )
    }

}

@Composable
fun MovieGenreContent(
    modifier: Modifier = Modifier,
    uiState: MovieGenreUiState,
    onIntent: (MovieGenreIntent) -> Unit,
    onGenreSelected: (Int) -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()

    PullToRefreshBox(
        modifier = modifier.fillMaxSize(),
        isRefreshing = uiState.isRefreshing,
        onRefresh = {
            onIntent(MovieGenreIntent.refreshPage)
        },
        state = pullToRefreshState
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            if (uiState.genres.isEmpty()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Image(
                        painter = painterResource(id = R.drawable.img_empty_placeholder),
                        contentDescription = null
                    )

                    Text("Data is empty")
                }
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
            uiState = MovieGenreUiState(
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
            uiState = MovieGenreUiState(),
            onIntent = {},
            onGenreSelected = {},
        )
    }
}