package com.omdbapi.data

import com.omdbapi.data.model.MovieResponse

internal class MovieRepository(private val api: GetMoviesService) {
    suspend fun getMovies(apiKey: String, searchQuery: String): Result<MovieResponse> {
        return try {
            val response = api.getMovies(apiKey, searchQuery)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch movies"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
