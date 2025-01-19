package com.omdbapi.data

import com.omdbapi.data.api.GetMoviesService
import com.omdbapi.data.model.MovieDetail
import com.omdbapi.data.model.MovieResponse

internal class MovieRepository(private val api: GetMoviesService) {
    // Fetches a list of movies based on the search query
    suspend fun getMovies(apiKey: String, searchQuery: String): Result<MovieResponse> {
        return try {
            // Makes the API call to fetch movies
            val response = api.getMovies(apiKey, searchQuery)
            // Checks if the response is successful and not null
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch movies"))
            }
        } catch (e: Exception) {
            Result.failure(e) // Returns failure in case of an exception
        }
    }

    // Fetches details of a specific movie by its ID
    suspend fun getMovieDetails(apiKey: String, movieId: String): Result<MovieDetail> {
        return try {
            // Makes the API call to fetch movie details
            val response = api.getMovieDetails(apiKey, movieId)
            // Checks if the response is successful and not null
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to fetch detail movies"))
            }
        } catch (e: Exception) {
            Result.failure(e) // Returns failure in case of an exception
        }
    }
}
