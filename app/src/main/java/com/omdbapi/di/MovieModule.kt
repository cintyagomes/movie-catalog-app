package com.omdbapi.di

import com.omdbapi.data.GetMoviesService
import com.omdbapi.data.MovieRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
internal class MovieModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://www.omdbapi.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): GetMoviesService = retrofit.create(
        GetMoviesService::class.java
    )

    @Provides
    @Singleton
    fun provideMovieRepository(api: GetMoviesService): MovieRepository = MovieRepository(api)
}
