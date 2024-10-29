package com.piashcse.hilt_mvvm_compose_movie.data.datasource.local.typeconverter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.CreatedBy
import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.Genre
import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.LastEpisodeToAir
import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.Network
import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.NextEpisodeToAir
import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.ProductionCompany
import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.ProductionCountry
import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.Season
import com.piashcse.hilt_mvvm_compose_movie.data.model.tv_series_detail.SpokenLanguage

class TvSeriesTypeConverter {
    private val gson : Gson by lazy {
        Gson()
    }

    @TypeConverter
    fun fromCreatedByList(value: List<CreatedBy>): String = gson.toJson(value)

    @TypeConverter
    fun toCreatedByList(value: String): List<CreatedBy> =
        gson.fromJson(value, object : TypeToken<List<CreatedBy>>() {}.type)

    @TypeConverter
    fun fromEpisodeRunTimeList(value: List<Int>): String = gson.toJson(value)

    @TypeConverter
    fun toEpisodeRunTimeList(value: String): List<Int> =
        gson.fromJson(value, object : TypeToken<List<Int>>() {}.type)

    @TypeConverter
    fun fromGenreList(value: List<Genre>): String = gson.toJson(value)

    @TypeConverter
    fun toGenreList(value: String): List<Genre> =
        gson.fromJson(value, object : TypeToken<List<Genre>>() {}.type)

    @TypeConverter
    fun fromLanguagesList(value: List<String>): String = gson.toJson(value)

    @TypeConverter
    fun toLanguagesList(value: String): List<String> =
        gson.fromJson(value, object : TypeToken<List<String>>() {}.type)

    @TypeConverter
    fun fromLastEpisodeToAir(value: LastEpisodeToAir?): String = gson.toJson(value)

    @TypeConverter
    fun toLastEpisodeToAir(value: String): LastEpisodeToAir? =
        gson.fromJson(value, LastEpisodeToAir::class.java)

    @TypeConverter
    fun fromNetworksList(value: List<Network>): String = gson.toJson(value)

    @TypeConverter
    fun toNetworksList(value: String): List<Network> =
        gson.fromJson(value, object : TypeToken<List<Network>>() {}.type)

    @TypeConverter
    fun fromNextEpisodeToAir(value: NextEpisodeToAir?): String = gson.toJson(value)

    @TypeConverter
    fun toNextEpisodeToAir(value: String): NextEpisodeToAir? =
        gson.fromJson(value, NextEpisodeToAir::class.java)

    @TypeConverter
    fun fromProductionCompaniesList(value: List<ProductionCompany>): String = gson.toJson(value)

    @TypeConverter
    fun toProductionCompaniesList(value: String): List<ProductionCompany> =
        gson.fromJson(value, object : TypeToken<List<ProductionCompany>>() {}.type)

    @TypeConverter
    fun fromProductionCountriesList(value: List<ProductionCountry>): String = gson.toJson(value)

    @TypeConverter
    fun toProductionCountriesList(value: String): List<ProductionCountry> =
        gson.fromJson(value, object : TypeToken<List<ProductionCountry>>() {}.type)

    @TypeConverter
    fun fromSeasonsList(value: List<Season>): String = gson.toJson(value)

    @TypeConverter
    fun toSeasonsList(value: String): List<Season> =
        gson.fromJson(value, object : TypeToken<List<Season>>() {}.type)

    @TypeConverter
    fun fromSpokenLanguagesList(value: List<SpokenLanguage>): String = gson.toJson(value)

    @TypeConverter
    fun toSpokenLanguagesList(value: String): List<SpokenLanguage> =
        gson.fromJson(value, object : TypeToken<List<SpokenLanguage>>() {}.type)

    @TypeConverter
    fun fromGenreListV2(genres: List<Genre>?): String? {
        return gson.toJson(genres)
    }

    @TypeConverter
    fun toGenreListV2(genresString: String?): List<Genre>? {
        return genresString?.let {
            val listType = object : TypeToken<List<Genre>>() {}.type
            gson.fromJson(it, listType)
        }
    }
}