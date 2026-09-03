package com.henrasn.nontonapa.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.henrasn.nontonapa.R
import com.henrasn.nontonapa.data.model.uimodel.movie.MovieUiData
import com.henrasn.nontonapa.ui.theme.NontonTheme

@Composable
fun MovieCard(modifier: Modifier = Modifier, movie: MovieUiData, onMovieSelected: () -> Unit) {
    ElevatedCard(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .aspectRatio(3/2f)
            .clickable(onClick = onMovieSelected),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomStart
        ) {
            ImageUrl(url = "https://image.tmdb.org/t/p/w500/" + movie.backdropPath.trimStart('/'))

            Column(modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                RateCapsule(rate = 8.7f)
                Text("Echoes of Saturn", style = MaterialTheme.typography.titleLarge)
                Text("Sci-Fi • Thriller", style = MaterialTheme.typography.titleSmall)
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewMovieCard() {
    NontonTheme {
        MovieCard(
            movie = MovieUiData(0, title = "title", backdropPath = "", releaseDate = "date", voteAverage = 8.9f)
        ){}
    }
}