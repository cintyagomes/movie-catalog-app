package com.omdbapi.di

import com.omdbapi.data.api.GetMoviesService
import com.omdbapi.data.MovieRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// Dagger module for providing movie-related dependencies
@Module
@InstallIn(SingletonComponent::class)
internal object MovieModule {
    // Provides a singleton instance of Retrofit configured for the OMDB API
    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://www.omdbapi.com/") // Sets the base URL for the API
        .addConverterFactory(GsonConverterFactory.create()) // Adds Gson converter for JSON parsing
        .build()

    // Provides a singleton instance of the GetMoviesService using the Retrofit instance
    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): GetMoviesService = retrofit.create(
        GetMoviesService::class.java // Creates the API service
    )

    // Provides a singleton instance of MovieRepository using the GetMoviesService instance
    @Provides
    @Singleton
    fun provideMovieRepository(api: GetMoviesService): MovieRepository = MovieRepository(api)
}
