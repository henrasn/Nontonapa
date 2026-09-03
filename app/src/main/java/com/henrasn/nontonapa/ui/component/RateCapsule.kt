package com.henrasn.nontonapa.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.henrasn.nontonapa.ui.theme.DarkGold
import com.henrasn.nontonapa.ui.theme.GoldenAmber
import com.henrasn.nontonapa.ui.theme.NontonTheme

@Composable
fun RateCapsule(modifier: Modifier = Modifier, rate: Float) {
    Row(
        modifier = modifier
            .background(GoldenAmber, shape = MaterialTheme.shapes.small)
            .padding(horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            modifier = Modifier.size(12.dp),
            imageVector = Icons.Filled.Star,
            contentDescription = "Star Rating",
            tint = DarkGold
        )

        Text(
            text = rate.toString(),
            color = DarkGold,
            fontSize = 12.sp
        )
    }
}

@Preview
@Composable
private fun PreviewCapsule() {
    NontonTheme {
        RateCapsule(
            rate = 8.7f
        )
    }
}