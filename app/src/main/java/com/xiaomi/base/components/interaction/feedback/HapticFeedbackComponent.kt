package com.xiaomi.base.components.interaction.feedback

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.Role

/**
 * Haptic Feedback Types
 * Different types of haptic feedback for various interactions
 */
enum class HapticFeedbackType {
    CLICK,
    LONG_PRESS,
    VIRTUAL_KEY,
    KEYBOARD_TAP,
    CONTEXT_CLICK,
    CLOCK_TICK,
    CONFIRM,
    REJECT,
    TEXT_HANDLE_MOVE
}

/**
 * Haptic Feedback Component
 * Provides haptic feedback for user interactions
 * 
 * @param modifier Modifier to be applied to the component
 * @param feedbackType Type of haptic feedback to provide
 * @param enabled Whether haptic feedback is enabled
 * @param onClick Callback when the component is clicked
 * @param content The composable content
 */
@Composable
fun HapticFeedbackComponent(
    modifier: Modifier = Modifier,
    feedbackType: HapticFeedbackType = HapticFeedbackType.CLICK,
    enabled: Boolean = true,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    val interactionSource = remember { MutableInteractionSource() }
    
    val performHapticFeedback = {
        if (enabled) {
            val hapticConstant = when (feedbackType) {
                HapticFeedbackType.CLICK -> HapticFeedbackConstants.VIRTUAL_KEY
                HapticFeedbackType.LONG_PRESS -> HapticFeedbackConstants.LONG_PRESS
                HapticFeedbackType.VIRTUAL_KEY -> HapticFeedbackConstants.VIRTUAL_KEY
                HapticFeedbackType.KEYBOARD_TAP -> HapticFeedbackConstants.KEYBOARD_TAP
                HapticFeedbackType.CONTEXT_CLICK -> HapticFeedbackConstants.CONTEXT_CLICK
                HapticFeedbackType.CLOCK_TICK -> HapticFeedbackConstants.CLOCK_TICK
                HapticFeedbackType.CONFIRM -> HapticFeedbackConstants.CONFIRM
                HapticFeedbackType.REJECT -> HapticFeedbackConstants.REJECT
                HapticFeedbackType.TEXT_HANDLE_MOVE -> HapticFeedbackConstants.TEXT_HANDLE_MOVE
            }
            view.performHapticFeedback(hapticConstant)
        }
    }
    
    Box(
        modifier = modifier.then(
            if (onClick != null) {
                Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null,
                    role = Role.Button
                ) {
                    performHapticFeedback()
                    onClick()
                }
            } else {
                Modifier
            }
        )
    ) {
        content()
    }
}

/**
 * Haptic Click Component
 * Simplified component for click haptic feedback
 * 
 * @param modifier Modifier to be applied to the component
 * @param enabled Whether haptic feedback is enabled
 * @param onClick Callback when the component is clicked
 * @param content The composable content
 */
@Composable
fun HapticClickComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    HapticFeedbackComponent(
        modifier = modifier,
        feedbackType = HapticFeedbackType.CLICK,
        enabled = enabled,
        onClick = onClick,
        content = content
    )
}

/**
 * Haptic Long Press Component
 * Component for long press haptic feedback
 * 
 * @param modifier Modifier to be applied to the component
 * @param enabled Whether haptic feedback is enabled
 * @param onLongPress Callback when the component is long pressed
 * @param content The composable content
 */
@Composable
fun HapticLongPressComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    onLongPress: () -> Unit,
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    
    val performHapticFeedback = {
        if (enabled) {
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
        }
    }
    
    Box(
        modifier = modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) {
            // Handle regular click if needed
        }
    ) {
        content()
    }
}

/**
 * Haptic Utility Functions
 * Utility functions for triggering haptic feedback programmatically
 */
class HapticFeedbackUtils {
    companion object {
        /**
         * Perform haptic feedback with specified type
         */
        fun performHapticFeedback(
            view: android.view.View,
            type: HapticFeedbackType
        ) {
            val hapticConstant = when (type) {
                HapticFeedbackType.CLICK -> HapticFeedbackConstants.VIRTUAL_KEY
                HapticFeedbackType.LONG_PRESS -> HapticFeedbackConstants.LONG_PRESS
                HapticFeedbackType.VIRTUAL_KEY -> HapticFeedbackConstants.VIRTUAL_KEY
                HapticFeedbackType.KEYBOARD_TAP -> HapticFeedbackConstants.KEYBOARD_TAP
                HapticFeedbackType.CONTEXT_CLICK -> HapticFeedbackConstants.CONTEXT_CLICK
                HapticFeedbackType.CLOCK_TICK -> HapticFeedbackConstants.CLOCK_TICK
                HapticFeedbackType.CONFIRM -> HapticFeedbackConstants.CONFIRM
                HapticFeedbackType.REJECT -> HapticFeedbackConstants.REJECT
                HapticFeedbackType.TEXT_HANDLE_MOVE -> HapticFeedbackConstants.TEXT_HANDLE_MOVE
            }
            view.performHapticFeedback(hapticConstant)
        }
        
        /**
         * Perform click haptic feedback
         */
        fun performClickFeedback(view: android.view.View) {
            view.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY)
        }
        
        /**
         * Perform long press haptic feedback
         */
        fun performLongPressFeedback(view: android.view.View) {
            view.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
        }
        
        /**
         * Perform success haptic feedback
         */
        fun performSuccessFeedback(view: android.view.View) {
            view.performHapticFeedback(HapticFeedbackConstants.CONFIRM)
        }
        
        /**
         * Perform error haptic feedback
         */
        fun performErrorFeedback(view: android.view.View) {
            view.performHapticFeedback(HapticFeedbackConstants.REJECT)
        }
    }
}

/**
 * Haptic Feedback Hook
 * Composable function that returns haptic feedback functions
 */
@Composable
fun rememberHapticFeedback(): HapticFeedbackController {
    val view = LocalView.current
    
    return remember {
        HapticFeedbackController(view)
    }
}

/**
 * Haptic Feedback Controller
 * Controller class for managing haptic feedback
 */
class HapticFeedbackController(private val view: android.view.View) {
    
    fun performFeedback(type: HapticFeedbackType) {
        HapticFeedbackUtils.performHapticFeedback(view, type)
    }
    
    fun click() {
        HapticFeedbackUtils.performClickFeedback(view)
    }
    
    fun longPress() {
        HapticFeedbackUtils.performLongPressFeedback(view)
    }
    
    fun success() {
        HapticFeedbackUtils.performSuccessFeedback(view)
    }
    
    fun error() {
        HapticFeedbackUtils.performErrorFeedback(view)
    }
    
    fun keyboardTap() {
        view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
    }
    
    fun clockTick() {
        view.performHapticFeedback(HapticFeedbackConstants.CLOCK_TICK)
    }
}