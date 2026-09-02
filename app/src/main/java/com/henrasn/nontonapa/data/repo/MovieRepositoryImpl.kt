package com.henrasn.nontonapa.data.repo

import com.henrasn.nontonapa.data.source.MovieDataSource
import com.henrasn.nontonapa.model.uimodel.genre.GenreUiData
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    val movieDataSource: MovieDataSource
) : MovieRepository {
    override suspend fun getMovieGenres(): Result<List<GenreUiData>> {

        return try {
            val response = movieDataSource.getMovieGenres()
            val result = response.genres.orEmpty().mapNotNull { responseListItem ->
                responseListItem?.let { item ->
                    GenreUiData(
                        id = item.id,
                        genreName = item.name.orEmpty()
                    )
                }
            }

            Result.success(result)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}