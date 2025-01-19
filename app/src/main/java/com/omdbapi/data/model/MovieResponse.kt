package com.omdbapi.data.model

import com.google.gson.annotations.SerializedName

// Data class representing the response from the movie API
data class MovieResponse(
    @SerializedName("Search")
    val search: List<Movie>?,
    @SerializedName("totalResults")
    val totalResults: String,
    @SerializedName("Response")
    val response: String
)

// Data class representing an individual movie
data class Movie(
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    @SerializedName("imdbID")
    val imdbId: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Poster")
    val poster: String
)
