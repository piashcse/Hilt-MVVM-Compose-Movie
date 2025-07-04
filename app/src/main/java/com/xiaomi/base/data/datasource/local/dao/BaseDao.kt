package com.xiaomi.base.data.datasource.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

/**
 * Base Data Access Object interface that provides common database operations.
 * All DAO interfaces should extend this interface to inherit these common operations.
 *
 * @param T the entity type this DAO operates on (must be non-null)
 */
interface BaseDao<T : Any> {
    
    /**
     * Insert an item in the database.
     * If the item already exists, it will be replaced.
     *
     * @param item the item to be inserted
     * @return the new rowId for the inserted item
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: T): Long
    
    /**
     * Insert multiple items in the database.
     * If any of the items already exist, they will be replaced.
     *
     * @param items the items to be inserted
     * @return the list of new rowIds for the inserted items
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<T>): List<Long>
    
    /**
     * Update an item in the database.
     *
     * @param item the item to be updated
     * @return the number of items updated
     */
    @Update
    suspend fun update(item: T): Int
    
    /**
     * Delete an item from the database.
     *
     * @param item the item to be deleted
     * @return the number of items deleted
     */
    @Delete
    suspend fun delete(item: T): Int
}