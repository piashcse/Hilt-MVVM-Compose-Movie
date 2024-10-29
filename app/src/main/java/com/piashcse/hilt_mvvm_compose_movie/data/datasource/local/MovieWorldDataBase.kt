package com.piashcse.hilt_mvvm_compose_movie.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.dao.FavoriteMovieDao
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.dao.FavoriteTvSeriesDao
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.typeconverter.MovieTypeConverter
import com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.typeconverter.TvSeriesTypeConverter
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail
import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.TvSeriesDetail

@TypeConverters(MovieTypeConverter::class, TvSeriesTypeConverter::class)
@Database(version = 1, entities = [MovieDetail::class, TvSeriesDetail::class], exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun getFavoriteMovieDetailDao(): FavoriteMovieDao
    abstract fun getFavoriteTvSeriesDao(): FavoriteTvSeriesDao
}