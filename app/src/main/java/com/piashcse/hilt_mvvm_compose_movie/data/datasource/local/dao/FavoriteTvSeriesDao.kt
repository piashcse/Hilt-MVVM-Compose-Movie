package com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.TvSeriesDetail

@Dao
interface FavoriteTvSeriesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieDetail: TvSeriesDetail)

    @Query("Select * From tvSeriesDetail Where id = :id")
    suspend fun getTvSeriesDetailById(id: Int): TvSeriesDetail?

    @Query("DELETE FROM tvSeriesDetail WHERE id = :id")
    suspend fun deleteTvSeriesById(id: Int)

    @Query("SELECT * FROM tvSeriesDetail")
    suspend fun getAllTvSeriesDetails(): List<TvSeriesDetail?>
}