package com.piashcse.hilt_mvvm_compose_movie.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem

@TypeConverters(IntTypeConverter::class)
@Database(version = 2, entities = [MovieEntity::class], exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getMovieDao(): MovieDao
}