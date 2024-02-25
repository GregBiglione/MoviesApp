package com.example.moviesapp.domain.repository

import com.example.moviesapp.data.remote.response.Movie
import com.example.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow

interface MovieListRepository {
    suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int): Flow<Resource<List<Movie>>>
    suspend fun getMovie(id: Int): Flow<Resource<Movie>>
}