package com.xiaomi.base.components.animation.basic

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.LinearEasing

/**
 * Fade In/Out Animation Component
 * Provides smooth fade in and fade out animations for any composable content
 * 
 * @param visible Controls the visibility state of the content
 * @param modifier Modifier to be applied to the component
 * @param durationMillis Duration of the animation in milliseconds
 * @param delayMillis Delay before animation starts in milliseconds
 * @param content The composable content to animate
 */
@Composable
fun FadeInOutComponent(
    visible: Boolean,
    modifier: Modifier = Modifier,
    durationMillis: Int = 300,
    delayMillis: Int = 0,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = durationMillis,
                delayMillis = delayMillis,
                easing = LinearEasing
            )
        ),
        exit = fadeOut(
            animationSpec = tween(
                durationMillis = durationMillis,
                delayMillis = delayMillis,
                easing = LinearEasing
            )
        )
    ) {
        content()
    }
}

/**
 * Fade In/Out Component with automatic state management
 * Automatically manages visibility state with toggle functionality
 * 
 * @param initialVisible Initial visibility state
 * @param modifier Modifier to be applied to the component
 * @param durationMillis Duration of the animation in milliseconds
 * @param delayMillis Delay before animation starts in milliseconds
 * @param onVisibilityChanged Callback when visibility changes
 * @param content The composable content to animate
 */
@Composable
fun AutoFadeInOutComponent(
    initialVisible: Boolean = true,
    modifier: Modifier = Modifier,
    durationMillis: Int = 300,
    delayMillis: Int = 0,
    onVisibilityChanged: ((Boolean) -> Unit)? = null,
    content: @Composable (toggleVisibility: () -> Unit) -> Unit
) {
    var visible by remember { mutableStateOf(initialVisible) }
    
    val toggleVisibility: () -> Unit = {
        visible = !visible
        onVisibilityChanged?.invoke(visible)
        Unit
    }
    
    Box(modifier = modifier) {
        FadeInOutComponent(
            visible = visible,
            durationMillis = durationMillis,
            delayMillis = delayMillis
        ) {
            content(toggleVisibility)
        }
    }
}

/**
 * Fade In/Out Component with custom easing
 * Provides more control over animation easing
 * 
 * @param visible Controls the visibility state of the content
 * @param modifier Modifier to be applied to the component
 * @param durationMillis Duration of the animation in milliseconds
 * @param delayMillis Delay before animation starts in milliseconds
 * @param easing Custom easing function for the animation
 * @param content The composable content to animate
 */
@Composable
fun CustomFadeInOutComponent(
    visible: Boolean,
    modifier: Modifier = Modifier,
    durationMillis: Int = 300,
    delayMillis: Int = 0,
    easing: androidx.compose.animation.core.Easing = LinearEasing,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = visible,
        modifier = modifier,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = durationMillis,
                delayMillis = delayMillis,
                easing = easing
            )
        ),
        exit = fadeOut(
            animationSpec = tween(
                durationMillis = durationMillis,
                delayMillis = delayMillis,
                easing = easing
            )
        )
    ) {
        content()
    }
}