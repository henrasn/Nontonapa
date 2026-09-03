package com.henrasn.nontonapa.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.henrasn.nontonapa.ui.theme.DarkGold
import com.henrasn.nontonapa.ui.theme.GentleSky
import com.henrasn.nontonapa.ui.theme.MorningMist
import com.henrasn.nontonapa.ui.theme.NontonTheme

@Composable
fun GenreItem(modifier: Modifier = Modifier, genreName: String, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .background(MorningMist, MaterialTheme.shapes.medium)
            .clickable(onClick = onClick),
        border = BorderStroke(1.dp, GentleSky)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = genreName,
                style = MaterialTheme.typography.titleLarge
            )

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = DarkGold
            )
        }
    }
}

@Preview(showSystemUi = false, showBackground = false)
@Composable
private fun PreviewGenreItem() {
    NontonTheme {
        GenreItem(
            genreName = "Sci-Fi",
            onClick = {}
        )

    }
}