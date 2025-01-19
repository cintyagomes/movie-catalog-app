package com.omdbapi.di

import com.omdbapi.data.MovieRepository
import dagger.Component
import javax.inject.Singleton

// Dagger component for dependency injection, marked as Singleton to provide a single instance
@Singleton
@Component(modules = [MovieModule::class])
internal interface AppComponent {
    // Exposes the MovieRepository for injection
    fun movieRepository(): MovieRepository
}
