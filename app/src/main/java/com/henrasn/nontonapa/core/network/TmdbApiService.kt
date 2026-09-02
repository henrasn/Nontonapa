package com.henrasn.nontonapa.core.network

import com.henrasn.nontonapa.model.dto.genre.MovieGenreResponse
import retrofit2.http.GET

interface TmdbApiService {
    //extract version
    @GET("genre/movie/list")
    suspend fun getMovieGenres(): MovieGenreResponse
}