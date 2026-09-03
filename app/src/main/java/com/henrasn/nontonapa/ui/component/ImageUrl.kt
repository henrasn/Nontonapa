package com.henrasn.nontonapa.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.henrasn.nontonapa.R

@Composable
fun ImageUrl(url: String) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = "List image",
        placeholder = painterResource(R.drawable.sample_poster),
        contentScale = ContentScale.Crop,
        error = painterResource(R.drawable.img_empty_placeholder),
        modifier = Modifier.fillMaxWidth()
    )
}