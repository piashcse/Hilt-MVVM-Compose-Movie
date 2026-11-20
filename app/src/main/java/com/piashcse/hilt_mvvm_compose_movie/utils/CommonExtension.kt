package com.piashcse.hilt_mvvm_compose_movie.utils

import androidx.navigation3.runtime.NavKey
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigator
import kotlin.math.pow
import kotlin.math.roundToInt
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



/**
 * Switches to a tab/root destination, keeping a single root on the back stack.
 */
fun Navigator.singleTopNavigator(route: NavKey) {
    navigateToRoot(route)
}