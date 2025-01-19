package com.omdbapi.data.api

import com.omdbapi.data.model.MovieDetail
import com.omdbapi.data.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

internal interface GetMoviesService {
    @GET("/")
    suspend fun getMovies(
        @Query("apikey") apiKey: String,
        @Query("s") searchQuery: String
    ): Response<MovieResponse>

    @GET("/")
    suspend fun getMovieDetails(
        @Query("apikey") apiKey: String,
        @Query("i") movieId: String,
    ): Response<MovieDetail>
}
