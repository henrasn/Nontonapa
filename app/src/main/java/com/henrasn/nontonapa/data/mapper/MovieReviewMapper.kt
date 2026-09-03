package com.henrasn.nontonapa.data.mapper

import com.henrasn.nontonapa.data.model.dto.moviereview.MovieReviewResponse
import com.henrasn.nontonapa.data.model.uimodel.moviereview.MovieReviewUiData

fun MovieReviewResponse.toMovieReviewUiDataList(): List<MovieReviewUiData> {
    return results.orEmpty().mapNotNull { item ->
        item?.let {
            MovieReviewUiData(
                name = it.author.orEmpty(),
                review = it.content.orEmpty(),
                date = it.createdAt.orEmpty()
            )
        }
    }
}
