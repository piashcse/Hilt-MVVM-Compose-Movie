package com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail

@Dao
interface MovieDetailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieDetail: MovieDetail)

    @Query("Select * From movieDetail Where id = :id")
    suspend fun getMovieDetailById(id: Int): MovieDetail?

    @Query("DELETE FROM movieDetail")
    suspend fun clearAll()
}