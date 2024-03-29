package com.example.moviesapp.data.local.movie

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MovieEntity::class],
    version = 1
)
abstract class MovieDatabase: RoomDatabase() {
    // Dao -----------------------------------------------------------------------------------------
    abstract val movieDao: MovieDao
}