package com.example.moviesapp.di

import com.example.moviesapp.data.repository.MovieListRepositoryImplementer
import com.example.moviesapp.domain.repository.MovieListRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMovieListRepository(
        movieListRepositoryImplementer: MovieListRepositoryImplementer): MovieListRepository
}