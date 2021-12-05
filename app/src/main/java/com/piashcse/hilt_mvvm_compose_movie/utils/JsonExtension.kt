package com.piashcse.hilt_mvvm_compose_movie.utils

import android.content.res.AssetManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

// Transform simple object to String with Gson
inline fun <reified T : Any> T.toPrettyJson() : String = Gson().toJson(this, T::class.java)

// Transform String Json to Object
inline fun <reified T : Any> String.fromPrettyJson() : T = Gson().fromJson(this , T::class.java)

// Transform String List Json to Object
inline fun <reified T : Any> String.fromPrettyJsonList() : MutableList <T> = when( this.isNotEmpty()){
    true -> Gson().fromJson(this, object : TypeToken<MutableList<T>>() {}.type)
    false -> mutableListOf()
}
// Read json file from assets
fun AssetManager.readAssetsFile(fileName: String): String =
    open(fileName).bufferedReader().use { it.readText() }