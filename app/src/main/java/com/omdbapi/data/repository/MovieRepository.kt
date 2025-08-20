package com.omdbapi.data.repository

import com.omdbapi.data.api.GetMoviesService
import com.omdbapi.domain.model.MovieDetail
import com.omdbapi.domain.model.MovieResponse
import retrofit2.Response

internal class MovieRepository(private val api: GetMoviesService) {
    // Fetches a list of movies based on the search query
    suspend fun getMovies(apiKey: String, searchQuery: String): Result<MovieResponse> {
        return try {
            validateResponse(api.getMovies(apiKey, searchQuery)) // Makes the API call to fetch data
        } catch (e: Exception) {
            Result.failure(e) // Returns failure in case of an exception
        }
    }

    // Fetches details of a specific movie by its ID
    suspend fun getMovieDetails(apiKey: String, movieId: String): Result<MovieDetail> {
        return try {
            validateResponse(api.getMovieDetails(apiKey, movieId)) // Makes the API call to fetch data
        } catch (e: Exception) {
            Result.failure(e) // Returns failure in case of an exception
        }
    }

    private fun <T> validateResponse(response: Response<T>): Result<T> {
        // Checks if the response is successful and not null
        return if (response.isSuccessful && response.body() != null) {
            Result.success(response.body()!!)
        } else {
            Result.failure(Exception("Failed to fetch data: ${response.message()}"))
        }
    }
}