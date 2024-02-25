package com.example.moviesapp.data.remote.response

data class MovieListDto(
    val page: Int,
    val results: List<Movie>,
    val totalPages: Int,
    val totalResults: Int
)