package com.piashcse.hilt_mvvm_compose_movie.data.datasource.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.piashcse.hilt_mvvm_compose_movie.data.model.moviedetail.Genre

class GenreTypeConverter {
    @TypeConverter
    fun fromGenreList(genres: List<Genre>?): String {
        return Gson().toJson(genres)
    }

    @TypeConverter
    fun toGenreList(genreString: String): List<Genre>? {
        val listType = object : TypeToken<List<Genre>>() {}.type
        return Gson().fromJson(genreString, listType)
    }
}