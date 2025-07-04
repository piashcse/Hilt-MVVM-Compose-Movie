package com.xiaomi.base.components.animation.advanced

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.*
import kotlin.random.Random

/**
 * Animation Type
 */
enum class AnimationType {
    FADE,
    SLIDE,
    SCALE,
    ROTATE,
    BOUNCE,
    ELASTIC,
    SPRING,
    MORPHING,
    PARTICLE,
    WAVE,
    RIPPLE,
    SHIMMER,
    PARALLAX,
    PHYSICS,
    CUSTOM
}

/**
 * Animation Direction
 */
enum class AnimationDirection {
    LEFT_TO_RIGHT,
    RIGHT_TO_LEFT,
    TOP_TO_BOTTOM,
    BOTTOM_TO_TOP,
    CENTER_OUT,
    OUT_TO_CENTER,
    CLOCKWISE,
    COUNTER_CLOCKWISE
}

/**
 * Animation Easing
 */
enum class AnimationEasing {
    LINEAR,
    EASE_IN,
    EASE_OUT,
    EASE_IN_OUT,
    BOUNCE,
    ELASTIC,
    OVERSHOOT,
    ANTICIPATE,
    CUSTOM
}

/**
 * Animation State
 */
enum class AnimationState {
    IDLE,
    RUNNING,
    PAUSED,
    COMPLETED,
    CANCELLED
}

/**
 * Animation Configuration
 */
data class AnimationConfig(
    val type: AnimationType = AnimationType.FADE,
    val duration: Int = 300,
    val delay: Int = 0,
    val easing: AnimationEasing = AnimationEasing.EASE_IN_OUT,
    val direction: AnimationDirection = AnimationDirection.LEFT_TO_RIGHT,
    val repeatCount: Int = 0, // 0 = no repeat, -1 = infinite
    val reverseOnRepeat: Boolean = false,
    val autoStart: Boolean = true,
    val customEasing: Easing? = null,
    val customParameters: Map<String, Any> = emptyMap()
)

/**
 * Particle Data
 */
data class Particle(
    val id: Int,
    var position: Offset,
    var velocity: Offset,
    var acceleration: Offset = Offset.Zero,
    var size: Float,
    var color: Color,
    var alpha: Float = 1f,
    var life: Float = 1f,
    var maxLife: Float = 1f,
    var rotation: Float = 0f,
    var rotationSpeed: Float = 0f
)

/**
 * Wave Data
 */
data class WaveData(
    val amplitude: Float,
    val frequency: Float,
    val phase: Float,
    val speed: Float,
    val color: Color,
    val strokeWidth: Float
)

/**
 * Advanced Animation Component
 * Comprehensive animation system with advanced effects
 * 
 * @param config Animation configuration
 * @param content Content to animate
 * @param onAnimationStart Callback when animation starts
 * @param onAnimationEnd Callback when animation ends
 */
@Composable
fun AdvancedAnimationComponent(
    config: AnimationConfig = AnimationConfig(),
    onAnimationStart: () -> Unit = {},
    onAnimationEnd: () -> Unit = {},
    content: @Composable () -> Unit
) {
    var animationState by remember { mutableStateOf(AnimationState.IDLE) }
    var isVisible by remember { mutableStateOf(!config.autoStart) }
    
    LaunchedEffect(config.autoStart) {
        if (config.autoStart) {
            delay(config.delay.toLong())
            isVisible = true
            animationState = AnimationState.RUNNING
            onAnimationStart()
        }
    }
    
    when (config.type) {
        AnimationType.FADE -> FadeAnimationContent(
            config = config,
            isVisible = isVisible,
            onAnimationEnd = {
                animationState = AnimationState.COMPLETED
                onAnimationEnd()
            },
            content = content
        )
        
        AnimationType.SLIDE -> SlideAnimationContent(
            config = config,
            isVisible = isVisible,
            onAnimationEnd = {
                animationState = AnimationState.COMPLETED
                onAnimationEnd()
            },
            content = content
        )
        
        AnimationType.SCALE -> ScaleAnimationContent(
            config = config,
            isVisible = isVisible,
            onAnimationEnd = {
                animationState = AnimationState.COMPLETED
                onAnimationEnd()
            },
            content = content
        )
        
        AnimationType.ROTATE -> RotateAnimationContent(
            config = config,
            isVisible = isVisible,
            onAnimationEnd = {
                animationState = AnimationState.COMPLETED
                onAnimationEnd()
            },
            content = content
        )
        
        AnimationType.BOUNCE -> BounceAnimationContent(
            config = config,
            isVisible = isVisible,
            onAnimationEnd = {
                animationState = AnimationState.COMPLETED
                onAnimationEnd()
            },
            content = content
        )
        
        AnimationType.ELASTIC -> ElasticAnimationContent(
            config = config,
            isVisible = isVisible,
            onAnimationEnd = {
                animationState = AnimationState.COMPLETED
                onAnimationEnd()
            },
            content = content
        )
        
        AnimationType.SPRING -> SpringAnimationContent(
            config = config,
            isVisible = isVisible,
            onAnimationEnd = {
                animationState = AnimationState.COMPLETED
                onAnimationEnd()
            },
            content = content
        )
        
        else -> {
            // Default to fade animation
            FadeAnimationContent(
                config = config,
                isVisible = isVisible,
                onAnimationEnd = {
                    animationState = AnimationState.COMPLETED
                    onAnimationEnd()
                },
                content = content
            )
        }
    }
}

/**
 * Particle System Component
 * Advanced particle effects
 * 
 * @param particleCount Number of particles
 * @param emissionRate Particles per second
 * @param particleLifetime Particle lifetime in seconds
 * @param gravity Gravity force
 * @param wind Wind force
 */
@Composable
fun ParticleSystemComponent(
    particleCount: Int = 50,
    emissionRate: Float = 10f,
    particleLifetime: Float = 3f,
    gravity: Offset = Offset(0f, 100f),
    wind: Offset = Offset(0f, 0f),
    colors: List<Color> = listOf(Color.Red, Color.Blue, Color.Green),
    modifier: Modifier = Modifier
) {
    var particles by remember { mutableStateOf<List<Particle>>(emptyList()) }
    var lastEmissionTime by remember { mutableStateOf(0L) }
    
    LaunchedEffect(Unit) {
        while (true) {
            val currentTime = System.currentTimeMillis()
            val deltaTime = (currentTime - lastEmissionTime) / 1000f
            
            // Update existing particles
            particles = particles.mapNotNull { particle ->
                val newLife = particle.life - deltaTime / particleLifetime
                if (newLife <= 0f) {
                    null
                } else {
                    particle.copy(
                        position = particle.position + particle.velocity * deltaTime,
                        velocity = particle.velocity + (particle.acceleration + gravity + wind) * deltaTime,
                        life = newLife,
                        alpha = newLife / particle.maxLife,
                        rotation = particle.rotation + particle.rotationSpeed * deltaTime
                    )
                }
            }
            
            // Emit new particles
            if (particles.size < particleCount && deltaTime >= 1f / emissionRate) {
                val newParticle = createRandomParticle(colors)
                particles = particles + newParticle
                lastEmissionTime = currentTime
            }
            
            delay(16) // ~60 FPS
        }
    }
    
    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        particles.forEach { particle ->
            drawParticle(particle)
        }
    }
}

/**
 * Wave Animation Component
 * Animated wave effects
 * 
 * @param waves List of wave configurations
 * @param speed Animation speed
 */
@Composable
fun WaveAnimationComponent(
    waves: List<WaveData> = listOf(
        WaveData(
            amplitude = 50f,
            frequency = 0.02f,
            phase = 0f,
            speed = 2f,
            color = Color.Blue,
            strokeWidth = 4f
        )
    ),
    speed: Float = 1f,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "wave")
    val time by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2 * PI.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = (2000 / speed).toInt(),
                easing = LinearEasing
            )
        ),
        label = "wave_time"
    )
    
    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        waves.forEach { wave ->
            drawWave(wave, time)
        }
    }
}

/**
 * Ripple Animation Component
 * Animated ripple effects
 * 
 * @param center Center point of ripples
 * @param rippleCount Number of ripples
 * @param maxRadius Maximum ripple radius
 * @param color Ripple color
 */
@Composable
fun RippleAnimationComponent(
    center: Offset? = null,
    rippleCount: Int = 3,
    maxRadius: Float = 200f,
    color: Color = Color.Blue,
    modifier: Modifier = Modifier
) {
    var ripples by remember { mutableStateOf<List<Float>>(emptyList()) }
    val density = LocalDensity.current
    
    LaunchedEffect(Unit) {
        while (true) {
            // Add new ripple
            ripples = (ripples + 0f).takeLast(rippleCount)
            
            // Update ripples
            repeat(10) {
                ripples = ripples.map { it + maxRadius / 10f }
                delay(50)
            }
            
            // Remove completed ripples
            ripples = ripples.filter { it < maxRadius }
            
            delay(500)
        }
    }
    
    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        val rippleCenter = center ?: Offset(size.width / 2, size.height / 2)
        
        ripples.forEach { radius ->
            val alpha = 1f - (radius / maxRadius)
            drawCircle(
                color = color.copy(alpha = alpha),
                radius = radius,
                center = rippleCenter,
                style = Stroke(width = 2.dp.toPx())
            )
        }
    }
}

/**
 * Shimmer Animation Component
 * Shimmer loading effect
 * 
 * @param isLoading Whether to show shimmer
 * @param shimmerColor Shimmer color
 * @param content Content to shimmer
 */
@Composable
fun ShimmerAnimationComponent(
    isLoading: Boolean = true,
    shimmerColor: Color = Color.Gray.copy(alpha = 0.3f),
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "shimmer")
    val shimmerTranslate by infiniteTransition.animateFloat(
        initialValue = -300f,
        targetValue = 300f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            )
        ),
        label = "shimmer_translate"
    )
    
    Box {
        content()
        
        if (isLoading) {
            Canvas(
                modifier = Modifier.matchParentSize()
            ) {
                val brush = Brush.linearGradient(
                    colors = listOf(
                        Color.Transparent,
                        shimmerColor,
                        Color.Transparent
                    ),
                    start = Offset(shimmerTranslate - 150f, 0f),
                    end = Offset(shimmerTranslate + 150f, size.height)
                )
                
                drawRect(
                    brush = brush,
                    size = size
                )
            }
        }
    }
}

/**
 * Parallax Animation Component
 * Parallax scrolling effect
 * 
 * @param layers List of parallax layers with different speeds
 * @param scrollOffset Current scroll offset
 */
@Composable
fun ParallaxAnimationComponent(
    layers: List<Pair<Float, @Composable () -> Unit>>, // speed, content
    scrollOffset: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        layers.forEach { (speed, content) ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset { IntOffset(0, (scrollOffset * speed).toInt()) }
            ) {
                content()
            }
        }
    }
}

/**
 * Physics Animation Component
 * Physics-based animations
 * 
 * @param initialPosition Initial position
 * @param targetPosition Target position
 * @param mass Object mass
 * @param stiffness Spring stiffness
 * @param damping Damping ratio
 */
@Composable
fun PhysicsAnimationComponent(
    initialPosition: Offset = Offset.Zero,
    targetPosition: Offset = Offset.Zero,
    mass: Float = 1f,
    stiffness: Float = 400f,
    damping: Float = 0.8f,
    content: @Composable (Offset) -> Unit
) {
    val animatedPosition = remember {
        Animatable(
            initialValue = initialPosition,
            typeConverter = Offset.VectorConverter
        )
    }
    
    LaunchedEffect(targetPosition) {
        animatedPosition.animateTo(
            targetValue = targetPosition,
            animationSpec = spring(
                dampingRatio = damping,
                stiffness = stiffness
            )
        )
    }
    
    content(animatedPosition.value)
}

/**
 * Morphing Animation Component
 * Shape morphing animations
 * 
 * @param shapes List of shapes to morph between
 * @param duration Animation duration
 * @param autoPlay Whether to auto-play the animation
 */
@Composable
fun MorphingAnimationComponent(
    shapes: List<Path>,
    duration: Int = 2000,
    autoPlay: Boolean = true,
    color: Color = Color.Blue,
    modifier: Modifier = Modifier
) {
    var currentShapeIndex by remember { mutableStateOf(0) }
    val morphProgress by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(
            durationMillis = duration,
            easing = EaseInOutCubic
        ),
        finishedListener = {
            if (autoPlay && shapes.size > 1) {
                currentShapeIndex = (currentShapeIndex + 1) % shapes.size
            }
        },
        label = "morph_progress"
    )
    
    LaunchedEffect(currentShapeIndex) {
        // Reset progress when shape changes
    }
    
    Canvas(
        modifier = modifier.fillMaxSize()
    ) {
        if (shapes.isNotEmpty()) {
            val currentShape = shapes[currentShapeIndex]
            val nextShape = shapes[(currentShapeIndex + 1) % shapes.size]
            
            // Interpolate between current and next shape
            val morphedPath = interpolatePaths(currentShape, nextShape, morphProgress)
            
            drawPath(
                path = morphedPath,
                color = color,
                style = Fill
            )
        }
    }
}

/**
 * Animation Sequence Component
 * Chain multiple animations in sequence
 * 
 * @param animations List of animation configurations
 * @param onSequenceComplete Callback when sequence completes
 */
@Composable
fun AnimationSequenceComponent(
    animations: List<AnimationConfig>,
    onSequenceComplete: () -> Unit = {},
    content: @Composable (Int) -> Unit // Current animation index
) {
    var currentAnimationIndex by remember { mutableStateOf(0) }
    var isPlaying by remember { mutableStateOf(true) }
    
    LaunchedEffect(currentAnimationIndex, isPlaying) {
        if (isPlaying && currentAnimationIndex < animations.size) {
            val currentAnimation = animations[currentAnimationIndex]
            delay((currentAnimation.duration + currentAnimation.delay).toLong())
            
            if (currentAnimationIndex < animations.size - 1) {
                currentAnimationIndex++
            } else {
                isPlaying = false
                onSequenceComplete()
            }
        }
    }
    
    if (isPlaying && currentAnimationIndex < animations.size) {
        content(currentAnimationIndex)
    }
}

/**
 * Interactive Animation Component
 * Gesture-driven animations
 * 
 * @param onDrag Callback for drag gestures
 * @param onTap Callback for tap gestures
 * @param content Content to make interactive
 */
@Composable
fun InteractiveAnimationComponent(
    onDrag: (Offset) -> Unit = {},
    onTap: (Offset) -> Unit = {},
    content: @Composable () -> Unit
) {
    var dragOffset by remember { mutableStateOf(Offset.Zero) }
    
    Box(
        modifier = Modifier
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    dragOffset += dragAmount
                    onDrag(dragOffset)
                }
            }
            .clickable { onTap(Offset.Zero) }
    ) {
        content()
    }
}

/**
 * Animation Content Implementations
 */

@Composable
private fun FadeAnimationContent(
    config: AnimationConfig,
    isVisible: Boolean,
    onAnimationEnd: () -> Unit,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn(
            animationSpec = tween(
                durationMillis = config.duration,
                easing = getEasing(config.easing)
            )
        ),
        exit = fadeOut(
            animationSpec = tween(
                durationMillis = config.duration,
                easing = getEasing(config.easing)
            )
        )
    ) {
        content()
    }
    
    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(config.duration.toLong())
            onAnimationEnd()
        }
    }
}

@Composable
private fun SlideAnimationContent(
    config: AnimationConfig,
    isVisible: Boolean,
    onAnimationEnd: () -> Unit,
    content: @Composable () -> Unit
) {
    val slideDirection = when (config.direction) {
        AnimationDirection.LEFT_TO_RIGHT -> AnimatedContentTransitionScope.SlideDirection.Right
        AnimationDirection.RIGHT_TO_LEFT -> AnimatedContentTransitionScope.SlideDirection.Left
        AnimationDirection.TOP_TO_BOTTOM -> AnimatedContentTransitionScope.SlideDirection.Down
        AnimationDirection.BOTTOM_TO_TOP -> AnimatedContentTransitionScope.SlideDirection.Up
        else -> AnimatedContentTransitionScope.SlideDirection.Right
    }
    
    AnimatedVisibility(
        visible = isVisible,
        enter = slideInHorizontally(
            animationSpec = tween(
                durationMillis = config.duration,
                easing = getEasing(config.easing)
            )
        ) { if (config.direction == AnimationDirection.LEFT_TO_RIGHT) -it else it },
        exit = slideOutHorizontally(
            animationSpec = tween(
                durationMillis = config.duration,
                easing = getEasing(config.easing)
            )
        ) { if (config.direction == AnimationDirection.LEFT_TO_RIGHT) it else -it }
    ) {
        content()
    }
    
    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(config.duration.toLong())
            onAnimationEnd()
        }
    }
}

@Composable
private fun ScaleAnimationContent(
    config: AnimationConfig,
    isVisible: Boolean,
    onAnimationEnd: () -> Unit,
    content: @Composable () -> Unit
) {
    AnimatedVisibility(
        visible = isVisible,
        enter = scaleIn(
            animationSpec = tween(
                durationMillis = config.duration,
                easing = getEasing(config.easing)
            ),
            initialScale = 0f
        ),
        exit = scaleOut(
            animationSpec = tween(
                durationMillis = config.duration,
                easing = getEasing(config.easing)
            ),
            targetScale = 0f
        )
    ) {
        content()
    }
    
    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(config.duration.toLong())
            onAnimationEnd()
        }
    }
}

@Composable
private fun RotateAnimationContent(
    config: AnimationConfig,
    isVisible: Boolean,
    onAnimationEnd: () -> Unit,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "rotate")
    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = if (config.direction == AnimationDirection.CLOCKWISE) 360f else -360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = config.duration,
                easing = getEasing(config.easing)
            ),
            repeatMode = if (config.reverseOnRepeat) RepeatMode.Reverse else RepeatMode.Restart
        ),
        label = "rotation"
    )
    
    Box(
        modifier = Modifier.rotate(if (isVisible) rotation else 0f)
    ) {
        content()
    }
    
    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(config.duration.toLong())
            onAnimationEnd()
        }
    }
}

@Composable
private fun BounceAnimationContent(
    config: AnimationConfig,
    isVisible: Boolean,
    onAnimationEnd: () -> Unit,
    content: @Composable () -> Unit
) {
    val bounceScale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        finishedListener = { onAnimationEnd() },
        label = "bounce_scale"
    )
    
    Box(
        modifier = Modifier.scale(bounceScale)
    ) {
        content()
    }
}

@Composable
private fun ElasticAnimationContent(
    config: AnimationConfig,
    isVisible: Boolean,
    onAnimationEnd: () -> Unit,
    content: @Composable () -> Unit
) {
    val elasticScale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioLowBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        finishedListener = { onAnimationEnd() },
        label = "elastic_scale"
    )
    
    Box(
        modifier = Modifier.scale(elasticScale)
    ) {
        content()
    }
}

@Composable
private fun SpringAnimationContent(
    config: AnimationConfig,
    isVisible: Boolean,
    onAnimationEnd: () -> Unit,
    content: @Composable () -> Unit
) {
    val springScale by animateFloatAsState(
        targetValue = if (isVisible) 1f else 0f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioNoBouncy,
            stiffness = Spring.StiffnessHigh
        ),
        finishedListener = { onAnimationEnd() },
        label = "spring_scale"
    )
    
    Box(
        modifier = Modifier.scale(springScale)
    ) {
        content()
    }
}

/**
 * Helper Functions
 */

/**
 * Get easing function
 */
private fun getEasing(easing: AnimationEasing): Easing {
    return when (easing) {
        AnimationEasing.LINEAR -> LinearEasing
        AnimationEasing.EASE_IN -> EaseIn
        AnimationEasing.EASE_OUT -> EaseOut
        AnimationEasing.EASE_IN_OUT -> EaseInOut
        AnimationEasing.BOUNCE -> EaseInOutBounce
        AnimationEasing.ELASTIC -> EaseInOutElastic
        AnimationEasing.OVERSHOOT -> EaseOutBack
        AnimationEasing.ANTICIPATE -> EaseInBack
        AnimationEasing.CUSTOM -> LinearEasing // Default fallback
    }
}

/**
 * Create random particle
 */
private fun createRandomParticle(colors: List<Color>): Particle {
    return Particle(
        id = Random.nextInt(),
        position = Offset(
            Random.nextFloat() * 400f,
            Random.nextFloat() * 200f
        ),
        velocity = Offset(
            Random.nextFloat() * 200f - 100f,
            Random.nextFloat() * 200f - 100f
        ),
        size = Random.nextFloat() * 10f + 5f,
        color = colors.random(),
        life = 1f,
        maxLife = 1f,
        rotationSpeed = Random.nextFloat() * 360f - 180f
    )
}

/**
 * Draw particle
 */
private fun DrawScope.drawParticle(particle: Particle) {
    drawCircle(
        color = particle.color.copy(alpha = particle.alpha),
        radius = particle.size,
        center = particle.position
    )
}

/**
 * Draw wave
 */
private fun DrawScope.drawWave(wave: WaveData, time: Float) {
    val path = Path()
    val points = mutableListOf<Offset>()
    
    for (x in 0..size.width.toInt() step 5) {
        val y = size.height / 2 + wave.amplitude * sin(
            wave.frequency * x + wave.phase + time * wave.speed
        )
        points.add(Offset(x.toFloat(), y))
    }
    
    if (points.isNotEmpty()) {
        path.moveTo(points.first().x, points.first().y)
        points.drop(1).forEach { point ->
            path.lineTo(point.x, point.y)
        }
        
        drawPath(
            path = path,
            color = wave.color,
            style = Stroke(width = wave.strokeWidth)
        )
    }
}

/**
 * Interpolate between two paths
 */
private fun interpolatePaths(path1: Path, path2: Path, progress: Float): Path {
    // Simplified path interpolation
    // In a real implementation, you would need more sophisticated path morphing
    return if (progress < 0.5f) path1 else path2
}

/**
 * Custom Easing Functions
 */
val EaseInOutBounce = Easing { fraction ->
    if (fraction < 0.5f) {
        0.5f * bounceOut(1f - 2f * fraction)
    } else {
        0.5f * bounceOut(2f * fraction - 1f) + 0.5f
    }
}

val EaseInOutElastic = Easing { fraction ->
    if (fraction < 0.5f) {
        0.5f * elasticIn(2f * fraction)
    } else {
        0.5f * elasticOut(2f * fraction - 1f) + 0.5f
    }
}

val EaseOutBack = Easing { fraction ->
    val c1 = 1.70158f
    val c3 = c1 + 1f
    1f + c3 * (fraction - 1f).pow(3).toFloat() + c1 * (fraction - 1f).pow(2).toFloat()
}

val EaseInBack = Easing { fraction ->
    val c1 = 1.70158f
    val c3 = c1 + 1f
    c3 * fraction.pow(3).toFloat() - c1 * fraction.pow(2).toFloat()
}

private fun bounceOut(t: Float): Float {
    return when {
        t < 1f / 2.75f -> 7.5625f * t * t
        t < 2f / 2.75f -> {
            val t2 = t - 1.5f / 2.75f
            7.5625f * t2 * t2 + 0.75f
        }
        t < 2.5f / 2.75f -> {
            val t2 = t - 2.25f / 2.75f
            7.5625f * t2 * t2 + 0.9375f
        }
        else -> {
            val t2 = t - 2.625f / 2.75f
            7.5625f * t2 * t2 + 0.984375f
        }
    }
}

private fun elasticIn(t: Float): Float {
    val c4 = (2f * PI) / 3f
    return when {
        t == 0f -> 0f
        t == 1f -> 1f
        else -> -(2f.pow(10f * (t - 1f)).toFloat()) * sin((t - 1f) * c4).toFloat()
    }
}

private fun elasticOut(t: Float): Float {
    val c4 = (2f * PI) / 3f
    return when {
        t == 0f -> 0f
        t == 1f -> 1f
        else -> 2f.pow(-10f * t).toFloat() * sin(t * c4).toFloat() + 1f
    }
}