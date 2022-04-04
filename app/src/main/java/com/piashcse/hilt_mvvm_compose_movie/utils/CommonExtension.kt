package com.piashcse.hilt_mvvm_compose_movie.utils

import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.paging.compose.LazyPagingItems
import okhttp3.ResponseBody
import org.json.JSONObject
import kotlin.time.Duration.Companion.minutes

fun ResponseBody.jsonData(): JSONObject {
    return JSONObject(this.string())
}

fun <T: Any> LazyGridScope.items(
    lazyPagingItems: LazyPagingItems<T>,
    itemContent: @Composable LazyGridItemScope.(value: T?) -> Unit
) {
    items(lazyPagingItems.itemCount) { index ->
        itemContent(lazyPagingItems[index])
    }
}

fun Int.hourMinutes(): String{
    return "${this.minutes.inWholeHours}h ${this % 60}m"
}
