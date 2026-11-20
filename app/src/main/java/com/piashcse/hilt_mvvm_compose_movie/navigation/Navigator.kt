package com.piashcse.hilt_mvvm_compose_movie.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.Snapshot
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack

/**
 * Small abstraction over the Navigation 3 back stack so screens can keep
 * navigating with simple [navigate]/[popBackStack] calls.
 */
@Stable
class Navigator(
    val backStack: NavBackStack<NavKey>,
    private val startKey: NavKey,
) {

    /** The route currently shown on screen. */
    val currentKey: NavKey
        get() = backStack.last()

    /** True when there is a previous screen to return to. */
    val canNavigateBack: Boolean
        get() = backStack.size > 1

    /**
     * Pushes [key] on the back stack. Re-navigating to the screen that is
     * already on top is a no-op, which keeps taps from stacking duplicates.
     */
    fun navigate(key: NavKey) {
        if (backStack.lastOrNull() == key) return
        backStack.add(key)
    }

    /** Pops the current screen. Returns false when the back stack is at its root. */
    fun popBackStack(): Boolean {
        if (!canNavigateBack) return false
        backStack.removeAt(backStack.lastIndex)
        return true
    }

    /**
     * Switches to a tab/root destination. The start destination is kept at the
     * bottom of the stack so system back returns to the home screen, matching
     * the previous navigation behaviour.
     */
    fun navigateToRoot(key: NavKey) {
        if (backStack.size == 1 && backStack.first() == key) return
        Snapshot.withMutableSnapshot {
            backStack.clear()
            if (key != startKey) backStack.add(startKey)
            backStack.add(key)
        }
    }
}

@Composable
fun rememberNavigator(startKey: NavKey): Navigator {
    val backStack = rememberNavBackStack(startKey)
    return remember(backStack) { Navigator(backStack, startKey) }
}
