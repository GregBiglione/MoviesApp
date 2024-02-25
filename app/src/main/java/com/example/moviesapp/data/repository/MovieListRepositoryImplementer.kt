package com.example.moviesapp.data.repository

import com.example.moviesapp.data.local.movie.MovieDatabase
import com.example.moviesapp.data.mapper.toMovie
import com.example.moviesapp.data.mapper.toMovieEntity
import com.example.moviesapp.data.remote.MovieApi
import com.example.moviesapp.data.remote.response.MovieDto
import com.example.moviesapp.domain.model.Movie
import com.example.moviesapp.domain.repository.MovieListRepository
import com.example.moviesapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class MovieListRepositoryImplementer @Inject constructor(private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase) : MovieListRepository {
    override suspend fun getMovieList(
        forceFetchFromRemote: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {
        return flow {
            emit(Resource.Loading(true))

            val localMovieList = movieDatabase.movieDao.getMovieByCategory(category)
            val shouldLoadLocalList = localMovieList.isNotEmpty() && !forceFetchFromRemote

            // Load data from local Database -------------------------------------------------------
            if(shouldLoadLocalList) {
                emit(Resource.Success(
                    data = localMovieList.map { movieEntity ->
                        movieEntity.toMovie(category)
                    }
                ))
                emit(Resource.Loading(false))

                return@flow
            }

            // Load data from Api ------------------------------------------------------------------
            val movieListFromApi = try {
                movieApi.getMoviesList(category, page)
            }
            catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            }
            catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            }
            catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading movies"))
                return@flow
            }

            val moviesEntities = movieListFromApi.results.let {
                it.map { movieDto ->
                   movieDto.toMovieEntity(category)
                }
            }

            movieDatabase.movieDao.upsertMovieList(moviesEntities)
            emit(Resource.Success(
                moviesEntities.map {
                    it.toMovie(category)
                }
            ))
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {
        return flow {
            emit(Resource.Loading(true))

            val movieEntity = movieDatabase.movieDao.getMovie(id)

            if(movieEntity != null) {
                emit(Resource.Success(
                    movieEntity.toMovie(movieEntity.category)
                ))
                emit(Resource.Loading(false))

                return@flow
            }

            emit(Resource.Error(message = "Error no such movie"))
            emit(Resource.Loading(false))
        }
    }
}