package com.henrasn.nontonapa.ui.pages.reviewlist

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.henrasn.nontonapa.core.error.toUiText
import com.henrasn.nontonapa.data.model.uimodel.moviereview.MovieReviewUiData
import com.henrasn.nontonapa.ui.component.ReviewUiItem
import com.henrasn.nontonapa.ui.pages.movie.MoviesContent

@Composable
fun ReviewsScreen(movieId: Int,viewModel: ReviewListViewModel= hiltViewModel()) {
    val reviewsPaging: LazyPagingItems<MovieReviewUiData> =
        viewModel.reviewsPaging.collectAsLazyPagingItems()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current

    val error = when {
        reviewsPaging.loadState.refresh is LoadState.Error ->
            (reviewsPaging.loadState.refresh as LoadState.Error).error

        reviewsPaging.loadState.append is LoadState.Error ->
            (reviewsPaging.loadState.append as LoadState.Error).error

        else -> null
    }

    if (error != null) {
        androidx.compose.runtime.LaunchedEffect(error) {
            snackbarHostState.currentSnackbarData?.dismiss()
            snackbarHostState.showSnackbar(error.toUiText().asString(context))
        }
    }

    LaunchedEffect(Unit) {
        viewModel.setMovie(movieId)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        ReviewsContent(
            modifier = Modifier.padding(innerPadding),
            reviewPaging = reviewsPaging,
        )
    }
}

@Composable
fun ReviewsContent(
    modifier: Modifier = Modifier,
    reviewPaging: LazyPagingItems<MovieReviewUiData>,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(
                count = reviewPaging.itemCount,
                key = reviewPaging.itemKey { it.id }
            ) { index ->
                reviewPaging[index]?.let { review ->
                    ReviewUiItem(review = review)
                }
            }

            when (reviewPaging.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                is LoadState.Error -> {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Couldn't load more reviews")
                            TextButton(onClick = { reviewPaging.retry() }) {
                                Text("Retry")
                            }
                        }
                    }
                }

                else -> {}
            }
        }
    }
}