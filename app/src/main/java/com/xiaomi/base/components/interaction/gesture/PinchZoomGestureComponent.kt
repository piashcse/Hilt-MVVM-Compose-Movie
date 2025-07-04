package com.xiaomi.base.components.interaction.gesture

import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.max
import kotlin.math.min

/**
 * Zoom Gesture Configuration
 * Configuration for pinch-to-zoom gesture detection
 */
data class ZoomGestureConfig(
    val minScale: Float = 0.5f,
    val maxScale: Float = 5.0f,
    val enableRotation: Boolean = false,
    val enablePan: Boolean = true,
    val doubleTapToZoom: Boolean = true,
    val doubleTapScale: Float = 2.0f,
    val animateTransitions: Boolean = true,
    val boundContent: Boolean = true // Keep content within bounds
)

/**
 * Zoom Gesture State
 * Holds the current state of zoom gesture
 */
data class ZoomGestureState(
    val scale: Float = 1f,
    val offset: Offset = Offset.Zero,
    val rotation: Float = 0f,
    val isZooming: Boolean = false,
    val isPanning: Boolean = false,
    val isRotating: Boolean = false
)

/**
 * Pinch Zoom Gesture Component
 * Handles pinch-to-zoom, pan, and rotation gestures
 * 
 * @param modifier Modifier to be applied to the component
 * @param config Configuration for zoom gesture detection
 * @param onZoomStart Callback when zoom starts
 * @param onZoomEnd Callback when zoom ends
 * @param onZoom Callback during zoom
 * @param onPan Callback during pan
 * @param onRotation Callback during rotation
 * @param content The zoomable content
 */
@Composable
fun PinchZoomGestureComponent(
    modifier: Modifier = Modifier,
    config: ZoomGestureConfig = ZoomGestureConfig(),
    onZoomStart: (() -> Unit)? = null,
    onZoomEnd: ((ZoomGestureState) -> Unit)? = null,
    onZoom: ((Float) -> Unit)? = null,
    onPan: ((Offset) -> Unit)? = null,
    onRotation: ((Float) -> Unit)? = null,
    content: @Composable (ZoomGestureState) -> Unit
) {
    var gestureState by remember { mutableStateOf(ZoomGestureState()) }
    var isGestureActive by remember { mutableStateOf(false) }
    
    Box(
        modifier = modifier
            .clip(RectangleShape)
            .pointerInput(Unit) {
                detectTransformGestures { centroid, pan, zoom, rotation ->
                    // Handle gesture start
                    if (!isGestureActive) {
                        isGestureActive = true
                        onZoomStart?.invoke()
                    }
                    val newScale = (gestureState.scale * zoom).coerceIn(
                        config.minScale,
                        config.maxScale
                    )
                    
                    val newOffset = if (config.enablePan) {
                        gestureState.offset + pan
                    } else {
                        gestureState.offset
                    }
                    
                    val newRotation = if (config.enableRotation) {
                        gestureState.rotation + rotation
                    } else {
                        gestureState.rotation
                    }
                    
                    gestureState = gestureState.copy(
                        scale = newScale,
                        offset = newOffset,
                        rotation = newRotation,
                        isZooming = zoom != 1f,
                        isPanning = pan != Offset.Zero,
                        isRotating = config.enableRotation && rotation != 0f
                    )
                    
                    // Trigger callbacks
                    if (zoom != 1f) onZoom?.invoke(newScale)
                    if (pan != Offset.Zero) onPan?.invoke(newOffset)
                    if (config.enableRotation && rotation != 0f) onRotation?.invoke(newRotation)
                    
                    // Simple gesture end detection
                    if (zoom == 1f && pan == Offset.Zero && rotation == 0f && isGestureActive) {
                        isGestureActive = false
                        onZoomEnd?.invoke(gestureState)
                    }
                }
            }
    ) {
        content(gestureState)
    }
}

/**
 * Zoomable Image Component
 * Pre-configured component for zoomable images
 * 
 * @param modifier Modifier to be applied to the component
 * @param config Zoom configuration
 * @param clipShape Shape to clip the content
 * @param content The image content
 */
@Composable
fun ZoomableImageComponent(
    modifier: Modifier = Modifier,
    config: ZoomGestureConfig = ZoomGestureConfig(
        minScale = 1f,
        maxScale = 4f,
        enableRotation = false,
        enablePan = true
    ),
    clipShape: Shape = RectangleShape,
    content: @Composable () -> Unit
) {
    PinchZoomGestureComponent(
        modifier = modifier.clip(clipShape),
        config = config
    ) { state ->
        Box(
            modifier = Modifier
                .graphicsLayer(
                    scaleX = state.scale,
                    scaleY = state.scale,
                    translationX = state.offset.x,
                    translationY = state.offset.y,
                    rotationZ = state.rotation
                )
        ) {
            content()
        }
    }
}

/**
 * Zoomable Content Component
 * General purpose zoomable content container
 * 
 * @param modifier Modifier to be applied to the component
 * @param config Zoom configuration
 * @param resetOnDoubleTap Reset zoom on double tap
 * @param content The zoomable content
 */
@Composable
fun ZoomableContentComponent(
    modifier: Modifier = Modifier,
    config: ZoomGestureConfig = ZoomGestureConfig(),
    resetOnDoubleTap: Boolean = true,
    content: @Composable (ZoomGestureState) -> Unit
) {
    var gestureState by remember { mutableStateOf(ZoomGestureState()) }
    
    PinchZoomGestureComponent(
        modifier = modifier,
        config = config,
        onZoomEnd = { state ->
            gestureState = state
        }
    ) { state ->
        content(state)
    }
}

/**
 * Zoom Controls Component
 * Provides zoom in/out buttons
 * 
 * @param modifier Modifier to be applied to the component
 * @param currentScale Current zoom scale
 * @param minScale Minimum zoom scale
 * @param maxScale Maximum zoom scale
 * @param onZoomIn Callback for zoom in
 * @param onZoomOut Callback for zoom out
 * @param onReset Callback for reset zoom
 * @param zoomStep Step size for zoom controls
 */
@Composable
fun ZoomControlsComponent(
    modifier: Modifier = Modifier,
    currentScale: Float,
    minScale: Float = 0.5f,
    maxScale: Float = 5.0f,
    onZoomIn: () -> Unit,
    onZoomOut: () -> Unit,
    onReset: () -> Unit,
    zoomStep: Float = 0.5f,
    content: @Composable (Boolean, Boolean, Boolean) -> Unit // canZoomIn, canZoomOut, canReset
) {
    val canZoomIn = currentScale < maxScale
    val canZoomOut = currentScale > minScale
    val canReset = currentScale != 1f
    
    Box(modifier = modifier) {
        content(canZoomIn, canZoomOut, canReset)
    }
}

/**
 * Advanced Zoom Component
 * Component with advanced zoom features like boundaries and smooth animations
 * 
 * @param modifier Modifier to be applied to the component
 * @param config Zoom configuration
 * @param contentSize Size of the content being zoomed
 * @param containerSize Size of the container
 * @param content The zoomable content
 */
@Composable
fun AdvancedZoomComponent(
    modifier: Modifier = Modifier,
    config: ZoomGestureConfig = ZoomGestureConfig(),
    contentSize: androidx.compose.ui.geometry.Size? = null,
    containerSize: androidx.compose.ui.geometry.Size? = null,
    content: @Composable (ZoomGestureState) -> Unit
) {
    var gestureState by remember { mutableStateOf(ZoomGestureState()) }
    
    // Calculate bounded offset if sizes are provided
    val boundedOffset = if (config.boundContent && contentSize != null && containerSize != null) {
        ZoomGestureUtils.calculateBoundedOffset(
            gestureState.offset,
            gestureState.scale,
            contentSize,
            containerSize
        )
    } else {
        gestureState.offset
    }
    
    PinchZoomGestureComponent(
        modifier = modifier,
        config = config,
        onZoomEnd = { state ->
            gestureState = state.copy(offset = boundedOffset)
        }
    ) { state ->
        val finalState = if (config.boundContent) {
            state.copy(offset = boundedOffset)
        } else {
            state
        }
        
        content(finalState)
    }
}

/**
 * Zoom Gesture Utilities
 */
class ZoomGestureUtils {
    companion object {
        /**
         * Calculate bounded offset to keep content within container
         */
        fun calculateBoundedOffset(
            currentOffset: Offset,
            scale: Float,
            contentSize: androidx.compose.ui.geometry.Size,
            containerSize: androidx.compose.ui.geometry.Size
        ): Offset {
            val scaledContentWidth = contentSize.width * scale
            val scaledContentHeight = contentSize.height * scale
            
            val maxOffsetX = max(0f, (scaledContentWidth - containerSize.width) / 2)
            val maxOffsetY = max(0f, (scaledContentHeight - containerSize.height) / 2)
            
            return Offset(
                x = currentOffset.x.coerceIn(-maxOffsetX, maxOffsetX),
                y = currentOffset.y.coerceIn(-maxOffsetY, maxOffsetY)
            )
        }
        
        /**
         * Calculate zoom to fit content in container
         */
        fun calculateFitScale(
            contentSize: androidx.compose.ui.geometry.Size,
            containerSize: androidx.compose.ui.geometry.Size
        ): Float {
            val scaleX = containerSize.width / contentSize.width
            val scaleY = containerSize.height / contentSize.height
            return min(scaleX, scaleY)
        }
        
        /**
         * Calculate zoom to fill container with content
         */
        fun calculateFillScale(
            contentSize: androidx.compose.ui.geometry.Size,
            containerSize: androidx.compose.ui.geometry.Size
        ): Float {
            val scaleX = containerSize.width / contentSize.width
            val scaleY = containerSize.height / contentSize.height
            return max(scaleX, scaleY)
        }
        
        /**
         * Create zoom config for image viewing
         */
        fun imageZoomConfig(
            minScale: Float = 0.5f,
            maxScale: Float = 4f
        ) = ZoomGestureConfig(
            minScale = minScale,
            maxScale = maxScale,
            enableRotation = false,
            enablePan = true,
            doubleTapToZoom = true,
            boundContent = true
        )
        
        /**
         * Create zoom config for document viewing
         */
        fun documentZoomConfig(
            minScale: Float = 0.25f,
            maxScale: Float = 3f
        ) = ZoomGestureConfig(
            minScale = minScale,
            maxScale = maxScale,
            enableRotation = true,
            enablePan = true,
            doubleTapToZoom = true,
            boundContent = true
        )
        
        /**
         * Create zoom config for map viewing
         */
        fun mapZoomConfig(
            minScale: Float = 0.1f,
            maxScale: Float = 10f
        ) = ZoomGestureConfig(
            minScale = minScale,
            maxScale = maxScale,
            enableRotation = true,
            enablePan = true,
            doubleTapToZoom = true,
            boundContent = false
        )
    }
}

/**
 * Zoom State Controller
 * Controller for programmatic zoom control
 */
class ZoomStateController {
    private var _state by mutableStateOf(ZoomGestureState())
    val state: ZoomGestureState get() = _state
    
    fun zoomIn(step: Float = 0.5f, maxScale: Float = 5f) {
        val newScale = min(_state.scale + step, maxScale)
        _state = _state.copy(scale = newScale)
    }
    
    fun zoomOut(step: Float = 0.5f, minScale: Float = 0.5f) {
        val newScale = max(_state.scale - step, minScale)
        _state = _state.copy(scale = newScale)
    }
    
    fun reset() {
        _state = ZoomGestureState()
    }
    
    fun setScale(scale: Float, minScale: Float = 0.5f, maxScale: Float = 5f) {
        val clampedScale = scale.coerceIn(minScale, maxScale)
        _state = _state.copy(scale = clampedScale)
    }
    
    fun setOffset(offset: Offset) {
        _state = _state.copy(offset = offset)
    }
    
    fun setRotation(rotation: Float) {
        _state = _state.copy(rotation = rotation)
    }
}

/**
 * Remember Zoom State Controller
 * Composable function to remember zoom state controller
 */
@Composable
fun rememberZoomStateController(): ZoomStateController {
    return remember { ZoomStateController() }
}