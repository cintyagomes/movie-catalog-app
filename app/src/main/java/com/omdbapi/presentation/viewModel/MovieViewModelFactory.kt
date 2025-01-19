package com.omdbapi.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.omdbapi.data.MovieRepository

// ViewModelFactory for creating an instance of MovieViewModel with the provided repository
internal class MovieViewModelFactory(
    private val repository: MovieRepository
) : ViewModelProvider.Factory {
    // Creates the ViewModel instance
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Checks if the requested ViewModel class is MovieViewModel
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST") // Suppresses the unchecked cast warning
            return MovieViewModel(repository) as T // Returns the MovieViewModel instance
        }
        // Throws an exception if the ViewModel class is unknown
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
