package com.henrasn.nontonapa.ui.pages.movie

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.henrasn.nontonapa.R
import com.henrasn.nontonapa.core.error.toUiText
import com.henrasn.nontonapa.data.model.uimodel.movie.MovieUiData
import com.henrasn.nontonapa.ui.component.MovieCard

@Composable
fun MoviesScreen(
    genreId: Int,
    viewModel: MovieViewModel = hiltViewModel(),
    onMovieSelected: (Int) -> Unit
) {
    val moviesPaging: LazyPagingItems<MovieUiData> =
        viewModel.moviesPaging.collectAsLazyPagingItems()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val error = when {
        moviesPaging.loadState.refresh is LoadState.Error ->
            (moviesPaging.loadState.refresh as LoadState.Error).error

        moviesPaging.loadState.append is LoadState.Error ->
            (moviesPaging.loadState.append as LoadState.Error).error

        else -> null
    }

    if (error != null) {
        androidx.compose.runtime.LaunchedEffect(error) {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(error.toUiText().asString(context))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setGenre(genreId)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        MoviesContent(
            modifier = Modifier.padding(innerPadding),
            moviesPaging = moviesPaging,
            onMovieSelected = onMovieSelected
        )
    }
}

@Composable
fun MoviesContent(
    modifier: Modifier = Modifier,
    moviesPaging: LazyPagingItems<MovieUiData>,
    onMovieSelected: (Int) -> Unit
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

//        if (moviesPaging.itemCount == 0) {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                Image(
//                    painter = painterResource(id = R.drawable.img_empty_placeholder),
//                    contentDescription = null
//                )
//                Text("Data is empty")
//            }
//        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(
                    count = moviesPaging.itemCount,
                    key = moviesPaging.itemKey { it.id }
                ) { index ->
                    moviesPaging[index]?.let { movie ->
                        MovieCard(
                            movie = movie,
                            onMovieSelected = { onMovieSelected(movie.id) }
                        )
                    }
                }

                when (moviesPaging.loadState.append) {
                    is LoadState.Loading -> {
                        item {
                            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    is LoadState.Error -> {
                        item {
                            Column(
                                modifier = Modifier.fillMaxWidth().padding(8.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("Couldn't load more movies")
                                TextButton(onClick = { moviesPaging.retry() }) {
                                    Text("Retry")
                                }
                            }
                        }
                    }
                    else -> {}
                }
            }
//        }
    }
}

