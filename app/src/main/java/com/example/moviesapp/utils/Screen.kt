package com.example.moviesapp.utils

sealed class Screen(val route: String) {
    object HomeScreen: Screen("main")
    object PopularMovieScreen: Screen("popularMovie")
    object UpcomingMovieScreen: Screen("upcomingMovie")
    object DetailScreen: Screen("detail")
}
