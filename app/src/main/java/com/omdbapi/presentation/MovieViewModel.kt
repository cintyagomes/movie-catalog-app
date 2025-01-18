package com.omdbapi.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omdbapi.data.MovieRepository
import com.omdbapi.data.model.Movie
import kotlinx.coroutines.launch

internal class MovieViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

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
}
