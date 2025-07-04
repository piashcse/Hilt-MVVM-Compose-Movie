package com.xiaomi.base.data.datasource.local.typeconverter

import androidx.room.TypeConverter
import java.util.Date

/**
 * Type converter for Room database to convert between Date and Long.
 * Room cannot store complex data types like Date directly, so we need to convert
 * them to and from types that Room can persist.
 */
class DateConverter {
    
    /**
     * Converts a timestamp value (Long) to a Date object.
     *
     * @param value the timestamp in milliseconds
     * @return the Date object, or null if the value is null
     */
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }
    
    /**
     * Converts a Date object to a timestamp (Long).
     *
     * @param date the Date object to convert
     * @return the timestamp in milliseconds, or null if the date is null
     */
    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }
}