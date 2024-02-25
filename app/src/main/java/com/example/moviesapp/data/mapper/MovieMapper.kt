package com.example.moviesapp.data.mapper

import com.example.moviesapp.data.local.movie.MovieEntity
import com.example.moviesapp.data.remote.response.MovieDto
import com.example.moviesapp.domain.model.Movie


// MovieDto to MovieEntity -------------------------------------------------------------------------

fun MovieDto.toMovieEntity(
    category: String
): MovieEntity {
    return MovieEntity(
        id = id ?: -1,
        adult = adult ?: false,
        backdropPath = backdropPath ?: "",
        genreIds = try {
            genreIds?.joinToString(",") ?: "-1, -2"
        }
        catch (e: Exception) {
            "-1, -2"
        },
        originalLanguage = originalLanguage ?: "",
        originalTitle = originalTitle ?: "",
        overview = overview ?: "",
        popularity = popularity ?: 0.0,
        posterPath = posterPath ?: "",
        releaseDate = releaseDate ?: "",
        title =  title ?: "",
        video = video ?: false,
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0,
        category = category,
    )
}

// MovieEntity to Movie ----------------------------------------------------------------------------

fun MovieEntity.toMovie(
    category: String
): Movie {
    return Movie(
        id = id,
        adult = adult,
        backdropPath = backdropPath,
        genreIds = try {
            genreIds.split(",").map {
                it.toInt()
            }
        }
        catch (e: Exception) {
            listOf(-1, -2)
        },
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount,
        category = category,
    )
}