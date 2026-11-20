package com.piashcse.hilt_mvvm_compose_movie.utils

import androidx.annotation.StringRes
import androidx.navigation3.runtime.NavKey
import com.piashcse.hilt_mvvm_compose_movie.R
import com.piashcse.hilt_mvvm_compose_movie.navigation.Navigator

/** Maps the TMDB gender id to a localised label resource. */
@StringRes
fun Int.genderRes(): Int = when (this) {
    1 -> R.string.gender_female
    2 -> R.string.gender_male
    else -> 0
}

/**
 * Switches to a tab/root destination, keeping a single root on the back stack.
 */
fun Navigator.singleTopNavigator(route: NavKey) {
    navigateToRoot(route)
}
