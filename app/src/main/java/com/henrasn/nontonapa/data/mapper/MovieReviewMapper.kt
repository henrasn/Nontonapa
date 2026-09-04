package com.henrasn.nontonapa.data.mapper

import com.henrasn.nontonapa.data.local.entity.ReviewEntity
import com.henrasn.nontonapa.data.model.dto.moviereview.MovieReviewResponse
import com.henrasn.nontonapa.data.model.dto.moviereview.ReviewItem
import com.henrasn.nontonapa.data.model.uimodel.moviereview.MovieReviewUiData

fun MovieReviewResponse.toMovieReviewUiDataList(): List<MovieReviewUiData> {
    return results.orEmpty().mapNotNull { item ->
        item?.let {
            MovieReviewUiData(
                id = it.id.orEmpty(),
                name = it.author.orEmpty(),
                review = it.content.orEmpty(),
                date = it.createdAt.orEmpty(),
                rating = it.authorDetails?.rating ?: 0f
            )
        }
    }
}

fun ReviewItem.toReviewEntity(movieId: Int, sortOrder: Int): ReviewEntity {
    return ReviewEntity(
        id = id.orEmpty(),
        movieId = movieId,
        name = author.orEmpty(),
        review = content.orEmpty(),
        date = createdAt.orEmpty(),
        rating = authorDetails?.rating ?: 0f,
        sortOrder = sortOrder
    )
}

fun ReviewEntity.toMovieReviewUiData(): MovieReviewUiData {
    return MovieReviewUiData(
        id=id,
        name = name,
        review = review,
        date = date,
        rating = rating
    )
}
