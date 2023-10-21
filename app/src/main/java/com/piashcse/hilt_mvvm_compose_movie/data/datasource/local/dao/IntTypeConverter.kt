package com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.dao

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IntTypeConverter {
    @TypeConverter
    fun saveIntList(list: List<Int>): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun getIntList(list: String): List<Int> {
        return Gson().fromJson(
            list,
            object : TypeToken<List<Int>>() {}.type
        )
    }
}