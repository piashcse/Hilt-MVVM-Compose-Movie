package com.piashcse.hilt_mvvm_compose_movie.utils

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.minutes

fun Int.hourMinutes(): String {
    return "${this.minutes.inWholeHours}h ${this % 60}m"
}

fun Int.genderInString(): String {
    return when (this) {
        1 -> "Female"
        2 -> "Male"
        else -> ""
    }
}

fun Double.roundTo(numFractionDigits: Int): Double {
    val factor = 10.0.pow(numFractionDigits.toDouble())
    return (this * factor).roundToInt() / factor
}



fun <T : Any> NavController.singleTopNavigator(route: T) {
    this.navigate(route) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}