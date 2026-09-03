package com.henrasn.nontonapa.ui.pages.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.henrasn.nontonapa.data.model.uimodel.movie.MovieUiData
import com.henrasn.nontonapa.ui.component.MovieCard

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
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        uiState.movie?.let {
            MovieCard(
                movie = MovieUiData(
                    it.id,
                    it.title,
                    it.backdropPath,
                    it.releaseDate,
                    it.voteAverage
                )
            ) { }
        }

        Spacer(Modifier.size(16.dp))
        Text(text = "Overview", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.size(4.dp))
        Text(text = uiState.movie?.overview.toString(), style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.size(16.dp))
        Text(text = "Tagline", style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.size(4.dp))
        Text(text = uiState.movie?.tagline.toString(), style = MaterialTheme.typography.bodyMedium)
    }
}