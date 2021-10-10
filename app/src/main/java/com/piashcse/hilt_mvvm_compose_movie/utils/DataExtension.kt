package com.piashcse.hilt_mvvm_compose_movie.utils

import okhttp3.ResponseBody
import org.json.JSONObject

fun ResponseBody.jsonData(): JSONObject {
    return JSONObject(this.string())
}