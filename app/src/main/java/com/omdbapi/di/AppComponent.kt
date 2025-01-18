package com.omdbapi.di

import com.omdbapi.data.MovieRepository
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MovieModule::class])
internal interface AppComponent {
    fun movieRepository(): MovieRepository
}
