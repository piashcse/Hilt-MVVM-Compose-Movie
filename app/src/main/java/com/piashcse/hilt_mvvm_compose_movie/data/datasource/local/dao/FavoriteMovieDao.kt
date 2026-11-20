package com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.MovieDetail
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieDetail: MovieDetail)

    @Query("SELECT * FROM movieDetail WHERE id = :id")
    suspend fun getMovieDetailById(id: Int): MovieDetail?

    @Query("SELECT * FROM movieDetail WHERE id = :id")
    fun observeMovieDetailById(id: Int): Flow<MovieDetail?>

    @Query("DELETE FROM movieDetail WHERE id = :id")
    suspend fun deleteMovieDetailById(id: Int)

    @Query("SELECT * FROM movieDetail ORDER BY id DESC")
    fun observeAll(): Flow<List<MovieDetail>>
}
