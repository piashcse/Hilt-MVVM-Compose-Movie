package com.piashcse.hilt_mvvm_compose_movie.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/***
 * When we get the last item loaded from the PagingState, there's no way to know the index of the page it belonged to.
 * To solve this problem, we can add another table that stores the next and previous page keys for each Movie.
 */
@Entity(tableName = "remote_key")
data class RemoteKeys(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "movie_id")
    val movieID: Int,
    val prevKey: Int?,
    val currentPage: Int,
    val nextKey: Int?,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
