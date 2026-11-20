package com.piashcse.hilt_mvvm_compose_movie.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale

private const val TMDB_DATE_PATTERN = "yyyy-MM-dd"

/** Formats a TMDB `yyyy-MM-dd` date using the user's locale. */
fun String?.asDisplayDate(): String? {
    if (isNullOrBlank()) return null
    return runCatching {
        val input = SimpleDateFormat(TMDB_DATE_PATTERN, Locale.US)
        val date = input.parse(this) ?: return null
        DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault()).format(date)
    }.getOrNull()
}

/** Two-letter language code (`en`) to a display name (`English`). */
fun String?.asLanguageName(): String? {
    if (isNullOrBlank()) return null
    val display = Locale.forLanguageTag(this).getDisplayLanguage(Locale.getDefault())
    return display.ifBlank { this }
}

/** Rating with one decimal, e.g. `7.7`. */
fun Double.asRating(): String = String.format(Locale.getDefault(), "%.1f", this)

/** Minutes as `2h 39m` / `45m`. */
fun Int.asDuration(): String {
    val hours = this / 60
    val minutes = this % 60
    return when {
        hours > 0 && minutes > 0 -> "${hours}h ${minutes}m"
        hours > 0 -> "${hours}h"
        else -> "${minutes}m"
    }
}

/** Year from a TMDB date, used as a compact subtitle. */
fun String?.asYear(): String? =
    this?.takeIf { it.length >= 4 && it[0].isDigit() }?.substring(0, 4)

/** Vote counts as `950`, `12.3K`, `1.2M`. */
fun Int.asCompactCount(): String {
    val value = toLong()
    return when {
        value >= 1_000_000 -> String.format(Locale.getDefault(), "%.1fM", value / 1_000_000f)
        value >= 1_000 -> String.format(Locale.getDefault(), "%.1fK", value / 1_000f)
        else -> value.toString()
    }.replace(".0K", "K").replace(".0M", "M")
}
