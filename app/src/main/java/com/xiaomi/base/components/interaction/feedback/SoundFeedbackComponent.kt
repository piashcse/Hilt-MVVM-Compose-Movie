package com.xiaomi.base.components.interaction.feedback

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import kotlinx.coroutines.launch

/**
 * Sound Feedback Types
 * Different types of sound feedback for various interactions
 */
enum class SoundFeedbackType {
    CLICK,
    SUCCESS,
    ERROR,
    WARNING,
    NOTIFICATION,
    KEYBOARD_TAP,
    BUTTON_PRESS,
    NAVIGATION,
    TOGGLE_ON,
    TOGGLE_OFF,
    SWIPE,
    LONG_PRESS,
    CUSTOM
}

/**
 * Sound Feedback Component
 * Provides audio feedback for user interactions
 * 
 * @param modifier Modifier to be applied to the component
 * @param soundType Type of sound feedback to provide
 * @param enabled Whether sound feedback is enabled
 * @param volume Volume level (0.0 to 1.0)
 * @param customSoundRes Custom sound resource ID (for CUSTOM type)
 * @param onClick Callback when the component is clicked
 * @param content The composable content
 */
@Composable
fun SoundFeedbackComponent(
    modifier: Modifier = Modifier,
    soundType: SoundFeedbackType = SoundFeedbackType.CLICK,
    enabled: Boolean = true,
    volume: Float = 1.0f,
    customSoundRes: Int? = null,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current
    val soundController = rememberSoundFeedbackController()
    val interactionSource = remember { MutableInteractionSource() }
    val scope = rememberCoroutineScope()
    
    val performSoundFeedback = {
        if (enabled) {
            scope.launch {
                when (soundType) {
                    SoundFeedbackType.CUSTOM -> {
                        customSoundRes?.let { soundController.playCustomSound(it, volume) }
                    }
                    else -> soundController.playSound(soundType, volume)
                }
            }
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
                    performSoundFeedback()
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
 * Sound Click Component
 * Simplified component for click sound feedback
 * 
 * @param modifier Modifier to be applied to the component
 * @param enabled Whether sound feedback is enabled
 * @param volume Volume level (0.0 to 1.0)
 * @param onClick Callback when the component is clicked
 * @param content The composable content
 */
@Composable
fun SoundClickComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    volume: Float = 1.0f,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    SoundFeedbackComponent(
        modifier = modifier,
        soundType = SoundFeedbackType.CLICK,
        enabled = enabled,
        volume = volume,
        onClick = onClick,
        content = content
    )
}

/**
 * Sound Success Component
 * Component for success sound feedback
 * 
 * @param modifier Modifier to be applied to the component
 * @param enabled Whether sound feedback is enabled
 * @param volume Volume level (0.0 to 1.0)
 * @param onSuccess Callback when success action is triggered
 * @param content The composable content
 */
@Composable
fun SoundSuccessComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    volume: Float = 1.0f,
    onSuccess: () -> Unit,
    content: @Composable () -> Unit
) {
    SoundFeedbackComponent(
        modifier = modifier,
        soundType = SoundFeedbackType.SUCCESS,
        enabled = enabled,
        volume = volume,
        onClick = onSuccess,
        content = content
    )
}

/**
 * Sound Error Component
 * Component for error sound feedback
 * 
 * @param modifier Modifier to be applied to the component
 * @param enabled Whether sound feedback is enabled
 * @param volume Volume level (0.0 to 1.0)
 * @param onError Callback when error action is triggered
 * @param content The composable content
 */
@Composable
fun SoundErrorComponent(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    volume: Float = 1.0f,
    onError: () -> Unit,
    content: @Composable () -> Unit
) {
    SoundFeedbackComponent(
        modifier = modifier,
        soundType = SoundFeedbackType.ERROR,
        enabled = enabled,
        volume = volume,
        onClick = onError,
        content = content
    )
}

/**
 * Sound Feedback Controller
 * Controller class for managing sound feedback
 */
class SoundFeedbackController(private val context: Context) {
    private var soundPool: SoundPool? = null
    private val soundMap = mutableMapOf<SoundFeedbackType, Int>()
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    
    init {
        initializeSoundPool()
        loadDefaultSounds()
    }
    
    private fun initializeSoundPool() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()
        
        soundPool = SoundPool.Builder()
            .setMaxStreams(10)
            .setAudioAttributes(audioAttributes)
            .build()
    }
    
    private fun loadDefaultSounds() {
        // Load system sounds (these would be actual sound files in a real implementation)
        // For now, we'll use system sound effects
    }
    
    suspend fun playSound(type: SoundFeedbackType, volume: Float = 1.0f) {
        if (!isSoundEnabled()) return
        
        // Use system sound effects for basic feedback
        when (type) {
            SoundFeedbackType.CLICK, SoundFeedbackType.BUTTON_PRESS -> {
                audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK, volume)
            }
            SoundFeedbackType.KEYBOARD_TAP -> {
                audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_STANDARD, volume)
            }
            SoundFeedbackType.NAVIGATION -> {
                audioManager.playSoundEffect(AudioManager.FX_FOCUS_NAVIGATION_UP, volume)
            }
            else -> {
                // For other types, we would load custom sounds
                playCustomSoundEffect(type, volume)
            }
        }
    }
    
    suspend fun playCustomSound(soundRes: Int, volume: Float = 1.0f) {
        if (!isSoundEnabled()) return
        
        soundPool?.let { pool ->
            val soundId = pool.load(context, soundRes, 1)
            pool.setOnLoadCompleteListener { _, _, status ->
                if (status == 0) {
                    pool.play(soundId, volume, volume, 1, 0, 1.0f)
                }
            }
        }
    }
    
    private fun playCustomSoundEffect(type: SoundFeedbackType, volume: Float) {
        // This would play custom sound effects based on type
        // For now, we'll use system sounds as fallback
        when (type) {
            SoundFeedbackType.SUCCESS -> {
                // Play success sound (would be a custom sound file)
                audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK, volume)
            }
            SoundFeedbackType.ERROR -> {
                // Play error sound (would be a custom sound file)
                audioManager.playSoundEffect(AudioManager.FX_KEYPRESS_INVALID, volume)
            }
            SoundFeedbackType.WARNING -> {
                // Play warning sound (would be a custom sound file)
                audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK, volume * 0.8f)
            }
            SoundFeedbackType.NOTIFICATION -> {
                // Play notification sound (would be a custom sound file)
                audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK, volume)
            }
            SoundFeedbackType.TOGGLE_ON, SoundFeedbackType.TOGGLE_OFF -> {
                // Play toggle sound (would be a custom sound file)
                audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK, volume * 0.6f)
            }
            SoundFeedbackType.SWIPE -> {
                // Play swipe sound (would be a custom sound file)
                audioManager.playSoundEffect(AudioManager.FX_FOCUS_NAVIGATION_LEFT, volume)
            }
            SoundFeedbackType.LONG_PRESS -> {
                // Play long press sound (would be a custom sound file)
                audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK, volume * 1.2f)
            }
            else -> {
                audioManager.playSoundEffect(AudioManager.FX_KEY_CLICK, volume)
            }
        }
    }
    
    private fun isSoundEnabled(): Boolean {
        return audioManager.ringerMode != AudioManager.RINGER_MODE_SILENT
    }
    
    fun release() {
        soundPool?.release()
        soundPool = null
    }
    
    // Convenience methods
    suspend fun click(volume: Float = 1.0f) = playSound(SoundFeedbackType.CLICK, volume)
    suspend fun success(volume: Float = 1.0f) = playSound(SoundFeedbackType.SUCCESS, volume)
    suspend fun error(volume: Float = 1.0f) = playSound(SoundFeedbackType.ERROR, volume)
    suspend fun warning(volume: Float = 1.0f) = playSound(SoundFeedbackType.WARNING, volume)
    suspend fun notification(volume: Float = 1.0f) = playSound(SoundFeedbackType.NOTIFICATION, volume)
    suspend fun keyboardTap(volume: Float = 1.0f) = playSound(SoundFeedbackType.KEYBOARD_TAP, volume)
    suspend fun navigation(volume: Float = 1.0f) = playSound(SoundFeedbackType.NAVIGATION, volume)
    suspend fun toggleOn(volume: Float = 1.0f) = playSound(SoundFeedbackType.TOGGLE_ON, volume)
    suspend fun toggleOff(volume: Float = 1.0f) = playSound(SoundFeedbackType.TOGGLE_OFF, volume)
    suspend fun swipe(volume: Float = 1.0f) = playSound(SoundFeedbackType.SWIPE, volume)
    suspend fun longPress(volume: Float = 1.0f) = playSound(SoundFeedbackType.LONG_PRESS, volume)
}

/**
 * Sound Feedback Hook
 * Composable function that returns sound feedback controller
 */
@Composable
fun rememberSoundFeedbackController(): SoundFeedbackController {
    val context = LocalContext.current
    
    return remember {
        SoundFeedbackController(context)
    }
}

/**
 * Sound Feedback Utils
 * Utility functions for sound feedback
 */
class SoundFeedbackUtils {
    companion object {
        /**
         * Check if sound feedback is enabled in system settings
         */
        fun isSoundFeedbackEnabled(context: Context): Boolean {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            return audioManager.ringerMode != AudioManager.RINGER_MODE_SILENT
        }
        
        /**
         * Get system volume for sound effects
         */
        fun getSystemSoundVolume(context: Context): Float {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_SYSTEM)
            val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_SYSTEM)
            return if (maxVolume > 0) currentVolume.toFloat() / maxVolume else 0f
        }
        
        /**
         * Play system sound effect
         */
        fun playSystemSound(context: Context, effectType: Int, volume: Float = 1.0f) {
            val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            audioManager.playSoundEffect(effectType, volume)
        }
    }
}