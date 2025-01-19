package com.omdbapi.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omdbapi.data.MovieRepository
import com.omdbapi.data.model.Movie
import com.omdbapi.data.model.MovieDetail
import kotlinx.coroutines.launch

// ViewModel for managing movie data and handling UI-related logic
internal class MovieViewModel(
    private val repository: MovieRepository // Injected repository for fetching movie data
) : ViewModel() {

    // LiveData for holding the list of movies fetched from the repository
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    // LiveData for holding error messages
    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    // LiveData for holding the state of movie details fetching
    private val _movieDetails = MutableLiveData<UIState<MovieDetail>>()
    val movieDetails: LiveData<UIState<MovieDetail>> = _movieDetails

    // Fetches movies based on search query
    fun getMovies(apiKey: String, searchQuery: String) {
        viewModelScope.launch {
            val result = repository.getMovies(apiKey, searchQuery)
            result.onSuccess {
                _movies.postValue(it.search ?: emptyList()) // Updates the list of movies
            }
            result.onFailure {
                _error.postValue(it.message) // Updates error message in case of failure
            }
        }
    }

    // Fetches movie details by movie ID
    fun getMovieDetails(apiKey: String, movieId: String) {
        viewModelScope.launch {
            _movieDetails.postValue(UIState.Loading) // Sets the state to loading before the API call
            val result = repository.getMovieDetails(apiKey, movieId)
            result.onSuccess { movieDetail ->
                _movieDetails.postValue(UIState.Loaded(movieDetail)) // Updates the movie details on success
            }
            result.onFailure { exception ->
                _error.postValue(exception.message) // Updates error message in case of failure
            }
        }
    }

    // Sealed class representing different states of UI loading, success, and error
    sealed class UIState<out T> {
        data object Loading : UIState<Nothing>()
        data class Error(val message: String) : UIState<Nothing>()
        data class Loaded<out T>(val data: T) : UIState<T>()
    }
}
