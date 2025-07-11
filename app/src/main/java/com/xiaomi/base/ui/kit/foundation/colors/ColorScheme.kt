package com.xiaomi.base.ui.kit.foundation.colors

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme

/**
 * Xiaomi Base UI Kit Color Schemes
 * Provides light and dark color schemes following Material Design 3 guidelines
 * with Xiaomi brand colors.
 */
object XiaomiColorScheme {
    
    /**
     * Light color scheme for Xiaomi Base UI Kit
     */
    val Light: ColorScheme = lightColorScheme(
        primary = ColorTokens.Primary40,
        onPrimary = ColorTokens.Primary100,
        primaryContainer = ColorTokens.Primary90,
        onPrimaryContainer = ColorTokens.Primary10,
        
        secondary = ColorTokens.Secondary40,
        onSecondary = ColorTokens.Secondary100,
        secondaryContainer = ColorTokens.Secondary90,
        onSecondaryContainer = ColorTokens.Secondary10,
        
        tertiary = ColorTokens.Tertiary40,
        onTertiary = ColorTokens.Tertiary100,
        tertiaryContainer = ColorTokens.Tertiary90,
        onTertiaryContainer = ColorTokens.Tertiary10,
        
        error = ColorTokens.Error40,
        onError = ColorTokens.Error100,
        errorContainer = ColorTokens.Error90,
        onErrorContainer = ColorTokens.Error10,
        
        background = ColorTokens.Neutral99,
        onBackground = ColorTokens.Neutral10,
        
        surface = ColorTokens.Neutral99,
        onSurface = ColorTokens.Neutral10,
        surfaceVariant = ColorTokens.NeutralVariant90,
        onSurfaceVariant = ColorTokens.NeutralVariant30,
        
        outline = ColorTokens.NeutralVariant50,
        outlineVariant = ColorTokens.NeutralVariant80,
        
        scrim = ColorTokens.Neutral0,
        
        inverseSurface = ColorTokens.Neutral20,
        inverseOnSurface = ColorTokens.Neutral95,
        inversePrimary = ColorTokens.Primary80,
        
        surfaceTint = ColorTokens.Primary40
    )
    
    /**
     * Dark color scheme for Xiaomi Base UI Kit
     */
    val Dark: ColorScheme = darkColorScheme(
        primary = ColorTokens.Primary80,
        onPrimary = ColorTokens.Primary20,
        primaryContainer = ColorTokens.Primary30,
        onPrimaryContainer = ColorTokens.Primary90,
        
        secondary = ColorTokens.Secondary80,
        onSecondary = ColorTokens.Secondary20,
        secondaryContainer = ColorTokens.Secondary30,
        onSecondaryContainer = ColorTokens.Secondary90,
        
        tertiary = ColorTokens.Tertiary80,
        onTertiary = ColorTokens.Tertiary20,
        tertiaryContainer = ColorTokens.Tertiary30,
        onTertiaryContainer = ColorTokens.Tertiary90,
        
        error = ColorTokens.Error80,
        onError = ColorTokens.Error20,
        errorContainer = ColorTokens.Error30,
        onErrorContainer = ColorTokens.Error90,
        
        background = ColorTokens.Neutral10,
        onBackground = ColorTokens.Neutral90,
        
        surface = ColorTokens.Neutral10,
        onSurface = ColorTokens.Neutral90,
        surfaceVariant = ColorTokens.NeutralVariant30,
        onSurfaceVariant = ColorTokens.NeutralVariant80,
        
        outline = ColorTokens.NeutralVariant60,
        outlineVariant = ColorTokens.NeutralVariant30,
        
        scrim = ColorTokens.Neutral0,
        
        inverseSurface = ColorTokens.Neutral90,
        inverseOnSurface = ColorTokens.Neutral20,
        inversePrimary = ColorTokens.Primary40,
        
        surfaceTint = ColorTokens.Primary80
    )
}

/**
 * Extended color scheme for custom Xiaomi colors
 * These colors are not part of Material Design 3 but are specific to Xiaomi branding
 */
data class ExtendedColorScheme(
    val success: androidx.compose.ui.graphics.Color,
    val onSuccess: androidx.compose.ui.graphics.Color,
    val successContainer: androidx.compose.ui.graphics.Color,
    val onSuccessContainer: androidx.compose.ui.graphics.Color,
    
    val warning: androidx.compose.ui.graphics.Color,
    val onWarning: androidx.compose.ui.graphics.Color,
    val warningContainer: androidx.compose.ui.graphics.Color,
    val onWarningContainer: androidx.compose.ui.graphics.Color,
    
    val info: androidx.compose.ui.graphics.Color,
    val onInfo: androidx.compose.ui.graphics.Color,
    val infoContainer: androidx.compose.ui.graphics.Color,
    val onInfoContainer: androidx.compose.ui.graphics.Color
)

object XiaomiExtendedColorScheme {
    
    val Light = ExtendedColorScheme(
        success = ColorTokens.Success40,
        onSuccess = ColorTokens.Success100,
        successContainer = ColorTokens.Success90,
        onSuccessContainer = ColorTokens.Success10,
        
        warning = ColorTokens.Warning40,
        onWarning = ColorTokens.Warning100,
        warningContainer = ColorTokens.Warning90,
        onWarningContainer = ColorTokens.Warning10,
        
        info = ColorTokens.Info40,
        onInfo = ColorTokens.Info100,
        infoContainer = ColorTokens.Info90,
        onInfoContainer = ColorTokens.Info10
    )
    
    val Dark = ExtendedColorScheme(
        success = ColorTokens.Success80,
        onSuccess = ColorTokens.Success20,
        successContainer = ColorTokens.Success30,
        onSuccessContainer = ColorTokens.Success90,
        
        warning = ColorTokens.Warning80,
        onWarning = ColorTokens.Warning20,
        warningContainer = ColorTokens.Warning30,
        onWarningContainer = ColorTokens.Warning90,
        
        info = ColorTokens.Info80,
        onInfo = ColorTokens.Info20,
        infoContainer = ColorTokens.Info30,
        onInfoContainer = ColorTokens.Info90
    )
}