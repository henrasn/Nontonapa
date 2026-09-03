package com.henrasn.nontonapa.core.network

import com.henrasn.nontonapa.data.model.dto.genre.MovieGenreResponse
import com.henrasn.nontonapa.data.model.dto.movie.MovieDiscoverResponse
import com.henrasn.nontonapa.data.model.dto.moviedetail.MovieDetailResponse
import com.henrasn.nontonapa.data.model.dto.moviereview.MovieReviewResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApiService {
    @GET("3/genre/movie/list")
    suspend fun getMovieGenres(): MovieGenreResponse

    @GET("3/discover/movie")
    suspend fun getDiscoverMovies(
        @Query("page") page: Int,
        @Query("with_genres") genreId: Int
    ): MovieDiscoverResponse

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int
    ): MovieDetailResponse

    @GET("3/movie/{movie_id}/reviews")
    suspend fun getMovieReviews(
        @Path("movie_id") movieId: Int,
        @Query("page") page: Int = 1
    ): MovieReviewResponse
}