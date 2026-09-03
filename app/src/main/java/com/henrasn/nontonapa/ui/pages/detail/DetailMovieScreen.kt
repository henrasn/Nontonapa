package com.henrasn.nontonapa.ui.pages.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.henrasn.nontonapa.data.model.dto.moviereview.ReviewItem
import com.henrasn.nontonapa.data.model.uimodel.movie.MovieUiData
import com.henrasn.nontonapa.ui.component.MovieCard
import com.henrasn.nontonapa.ui.component.ReviewUiItem

@Composable
fun DetailMovieScreen(movieId: Int, viewModel: MovieDetailViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.onIntent(MovieDetailIntent.LoadMovieDetail(movieId))
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is MovieDetailEffect.ShowError -> {
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
        DetailMovieContent(modifier = Modifier.padding(innerPadding), uiState = uiState)
    }
}

@Composable
fun DetailMovieContent(modifier: Modifier = Modifier, uiState: MovieDetailUiState) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            uiState.movie?.let { movie ->
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    MovieCard(
                        movie = MovieUiData(
                            movie.id,
                            movie.title,
                            movie.backdropPath,
                            movie.releaseDate,
                            movie.voteAverage
                        )
                    ) { }

                    Spacer(Modifier.size(12.dp))
                    Text(text = "Overview", style = MaterialTheme.typography.titleLarge)
                    Text(text = movie.overview, style = MaterialTheme.typography.bodyMedium)
                    Spacer(Modifier.size(12.dp))
                    Text(text = "Tagline", style = MaterialTheme.typography.titleLarge)
                    Text(text = movie.tagline, style = MaterialTheme.typography.bodyMedium)
                }
            } ?: run {
                CircularProgressIndicator(
                    modifier = Modifier.width(64.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant,
                )
            }
        }

        if (uiState.reviews.isNotEmpty()) {
            item {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "See All",
                    style = MaterialTheme.typography.titleSmall,
                    textAlign = TextAlign.End
                )
            }
        }

        items(uiState.reviews) { review ->
            ReviewUiItem(review)
        }
    }
}