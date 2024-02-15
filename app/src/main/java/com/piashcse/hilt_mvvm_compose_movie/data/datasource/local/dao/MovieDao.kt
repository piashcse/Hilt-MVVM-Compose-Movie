package com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.piashcse.hilt_mvvm_compose_movie.data.model.MovieItem

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<MovieItem>)

    @Query("Select * From movies Order By page")
    fun getMovies(): PagingSource<Int, MovieItem>

    @Query("DELETE FROM movies")
    suspend fun clearAll()
}