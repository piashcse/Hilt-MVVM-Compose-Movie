package com.piashcse.hilt_mvvm_compose_movie.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.dao.MovieDetailDao
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail

@TypeConverters(IntTypeConverter::class, GenreTypeConverter::class)
@Database(version = 1, entities = [MovieDetail::class], exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDetailDao(): MovieDetailDao
}