package com.henrasn.nontonapa.ui.component

import androidx.compose.foundation.Image
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
import com.henrasn.nontonapa.ui.theme.NontonTheme

@Composable
fun LargeCard(modifier: Modifier = Modifier) {
    ElevatedCard(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth(0.7f)
            .aspectRatio(2 / 3f),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomStart
        ) {
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = painterResource(R.drawable.sample_poster),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )

            GradientWhite()

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
private fun PreviewLargeCard() {
    NontonTheme {
        LargeCard()
    }
}