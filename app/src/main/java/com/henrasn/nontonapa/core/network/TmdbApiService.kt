package com.henrasn.nontonapa.core.network

import com.henrasn.nontonapa.data.model.dto.genre.MovieGenreResponse
import retrofit2.http.GET

interface TmdbApiService {
    //extract version
    @GET("3/genre/movie/list")
    suspend fun getMovieGenres(): MovieGenreResponse
}