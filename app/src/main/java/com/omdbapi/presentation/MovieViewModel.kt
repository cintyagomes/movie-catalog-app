package com.omdbapi.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omdbapi.data.MovieRepository
import com.omdbapi.data.model.Movie
import com.omdbapi.data.model.MovieDetail
import kotlinx.coroutines.launch

internal class MovieViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _movieDetails = MutableLiveData<UIState<MovieDetail>>()
    val movieDetails: LiveData<UIState<MovieDetail>> = _movieDetails

    fun getMovies(apiKey: String, searchQuery: String) {
        viewModelScope.launch {
            val result = repository.getMovies(apiKey, searchQuery)
            result.onSuccess {
                Log.d("MovieViewModel", "Movies fetched: ${it.search}")
                _movies.postValue(it.search ?: emptyList())
            }
            result.onFailure {
                Log.e("MovieViewModel", "Error fetching movies: ${it.message}")
                _error.postValue(it.message)
            }
        }
    }

    fun getMovieDetails(apiKey: String, movieId: String) {
        viewModelScope.launch {
            _movieDetails.postValue(UIState.Loading)
            val result = repository.getMovieDetails(apiKey, movieId)
            result.onSuccess { movieDetail ->
                _movieDetails.postValue(UIState.Loaded(movieDetail))
            }
            result.onFailure { exception ->
                Log.e("MovieViewModel", "Error fetching movie details: ${exception.message}")
                _error.postValue(exception.message)
            }
        }
    }

    sealed class UIState<out T> {
        data object Loading : UIState<Nothing>()
        data class Error(val message: String) : UIState<Nothing>()
        data class Loaded<out T>(val data: T) : UIState<T>()
    }
}
