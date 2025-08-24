package com.omdbapi.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omdbapi.data.repository.MovieRepository
import com.omdbapi.domain.model.Movie
import com.omdbapi.domain.model.MovieDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.launch

// ViewModel for managing movie data and handling UI-related logic
@HiltViewModel
internal class MovieViewModel @Inject constructor(
    private val repository: MovieRepository // Injected repository for fetching movie data
) : ViewModel() {

    // LiveData for holding the list of movies fetched from the repository
    private val _movies = MutableLiveData<UIState<List<Movie>>>()
    val movies: LiveData<UIState<List<Movie>>> = _movies

    // LiveData for holding the state of movie details fetching
    private val _movieDetails = MutableLiveData<UIState<MovieDetail>>()
    val movieDetails: LiveData<UIState<MovieDetail>> = _movieDetails

    // Fetches movies based on search query
    fun fetchMovies(apiKey: String, searchQuery: String) {
        _movies.postValue(UIState.Loading) // Set state to Loading
        viewModelScope.launch {
            val result = repository.getMovies(apiKey, searchQuery)
            result.onSuccess { response ->
                _movies.postValue(UIState.Loaded(response.search ?: emptyList()))
            }.onFailure { error ->
                _movies.postValue(UIState.Error(error.message ?: "Unknown error"))
            }
        }
    }

    // Fetches movie details by movie ID
    fun fetchMovieDetails(apiKey: String, movieId: String) {
        _movieDetails.postValue(UIState.Loading) // Set state to Loading
        viewModelScope.launch {
            val result = repository.getMovieDetails(apiKey, movieId)
            result.onSuccess { details ->
                _movieDetails.postValue(UIState.Loaded(details))
            }.onFailure { error ->
                _movieDetails.postValue(UIState.Error(error.message ?: "Unknown error"))
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
