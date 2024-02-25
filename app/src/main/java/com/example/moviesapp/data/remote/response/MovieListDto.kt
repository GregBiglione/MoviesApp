package com.example.moviesapp.data.remote.response

data class MovieListDto(
    val page: Int,
    val results: List<MovieDto>,
    val totalPages: Int,
    val totalResults: Int
)