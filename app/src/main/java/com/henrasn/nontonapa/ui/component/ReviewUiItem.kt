package com.henrasn.nontonapa.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.henrasn.nontonapa.data.model.uimodel.moviereview.MovieReviewUiData
import com.henrasn.nontonapa.ui.theme.NontonTheme

@Composable
fun ReviewUiItem(review: MovieReviewUiData,modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
        ,
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),

    ){
    Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(16.dp)) {
        Text(text = review.name, style = MaterialTheme.typography.titleMedium)
        Text(text = review.date, style = MaterialTheme.typography.titleSmall)
        Spacer(Modifier.size(4.dp))
        Text(text = review.review, style = MaterialTheme.typography.bodyMedium)
    }
            }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
private fun PreviewReviewUiItem() {
    NontonTheme {
        ReviewUiItem(
            review = MovieReviewUiData(
                name = "name",
                date = "2026",
                review = "this is review"
            )
        )
    }
}