package com.piashcse.hilt_mvvm_compose_movie.data.datasource.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface MovieDao {

    @Upsert
    fun upsertAll(movies: List<MovieEntity>)

    @Query("SELECT * FROM movie")
    fun pagingSource(): PagingSource<Int, MovieEntity>

    @Query("DELETE FROM movie")
    fun clearAll()
}