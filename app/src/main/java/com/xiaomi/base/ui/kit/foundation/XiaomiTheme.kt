package com.xiaomi.base.ui.kit.foundation

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.xiaomi.base.ui.kit.foundation.spacing.LocalXiaomiSpacing
import com.xiaomi.base.ui.kit.foundation.spacing.XiaomiSpacing

/**
 * Extended colors for Xiaomi theme
 */
data class XiaomiExtendedColors(
    val success: Color,
    val onSuccess: Color,
    val successContainer: Color,
    val onSuccessContainer: Color,
    val warning: Color,
    val onWarning: Color,
    val warningContainer: Color,
    val onWarningContainer: Color
)

/**
 * Light extended colors
 */
private val XiaomiLightExtendedColors = XiaomiExtendedColors(
    success = Color(0xFF4CAF50),
    onSuccess = Color(0xFFFFFFFF),
    successContainer = Color(0xFFE8F5E8),
    onSuccessContainer = Color(0xFF1B5E20),
    warning = Color(0xFFFF9800),
    onWarning = Color(0xFFFFFFFF),
    warningContainer = Color(0xFFFFF3E0),
    onWarningContainer = Color(0xFFE65100)
)

/**
 * Dark extended colors
 */
private val XiaomiDarkExtendedColors = XiaomiExtendedColors(
    success = Color(0xFF81C784),
    onSuccess = Color(0xFF1B5E20),
    successContainer = Color(0xFF2E7D32),
    onSuccessContainer = Color(0xFFC8E6C9),
    warning = Color(0xFFFFB74D),
    onWarning = Color(0xFFE65100),
    warningContainer = Color(0xFFFF8F00),
    onWarningContainer = Color(0xFFFFE0B2)
)

/**
 * Local composition for extended colors
 */
val LocalXiaomiExtendedColors = staticCompositionLocalOf { XiaomiLightExtendedColors }

/**
 * Extension to access extended colors from MaterialTheme
 */
val MaterialTheme.extendedColors: XiaomiExtendedColors
    @Composable
    get() = LocalXiaomiExtendedColors.current

/**
 * Main theme provider for Xiaomi UI Kit
 * 
 * This composable provides the base Material3 theme with Xiaomi customizations.
 * It wraps the content with proper color schemes, typography, and spacing.
 */
@Composable
fun XiaomiTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S -> {
            val context = androidx.compose.ui.platform.LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> XiaomiDarkColorScheme
        else -> XiaomiLightColorScheme
    }

    val extendedColors = if (darkTheme) XiaomiDarkExtendedColors else XiaomiLightExtendedColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = XiaomiTypography,
        shapes = XiaomiShapes,
        content = {
            CompositionLocalProvider(
                LocalXiaomiSpacing provides XiaomiSpacing(),
                LocalXiaomiExtendedColors provides extendedColors,
                content = content
            )
        }
    )
}

/**
 * Preview theme wrapper for Xiaomi UI Kit
 * Used in @Preview functions throughout the UI Kit
 */
@Composable
fun XiaomiPreviewTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    XiaomiTheme(
        darkTheme = darkTheme,
        dynamicColor = false,
        content = content
    )
}

/**
 * Light color scheme for Xiaomi theme
 */
private val XiaomiLightColorScheme = lightColorScheme(
    primary = Color(0xFFFF6900),      // Xiaomi Orange
    onPrimary = Color(0xFFFFFFFF),
    primaryContainer = Color(0xFFFFDBCC),
    onPrimaryContainer = Color(0xFF341100),
    secondary = Color(0xFF77574A),
    onSecondary = Color(0xFFFFFFFF),
    secondaryContainer = Color(0xFFFFDBCC),
    onSecondaryContainer = Color(0xFF2C160C),
    tertiary = Color(0xFF6B5D2F),
    onTertiary = Color(0xFFFFFFFF),
    tertiaryContainer = Color(0xFFF4E1A7),
    onTertiaryContainer = Color(0xFF231B00),
    error = Color(0xFFBA1A1A),
    errorContainer = Color(0xFFFFDAD6),
    onError = Color(0xFFFFFFFF),
    onErrorContainer = Color(0xFF410002),
    background = Color(0xFFF8F8F8),
    onBackground = Color(0xFF221A16),
    surface = Color(0xFFFFF8F6),
    onSurface = Color(0xFF221A16),
    surfaceVariant = Color(0xFFF4DED4),
    onSurfaceVariant = Color(0xFF52443C),
    outline = Color(0xFF84746B),
    inverseOnSurface = Color(0xFFFDEEE7),
    inverseSurface = Color(0xFF382F2A),
    inversePrimary = Color(0xFFFFB690),
    surfaceTint = Color(0xFFFF6900)
)

/**
 * Dark color scheme for Xiaomi theme
 */
private val XiaomiDarkColorScheme = darkColorScheme(
    primary = Color(0xFFFFB690),
    onPrimary = Color(0xFF5A1E00),
    primaryContainer = Color(0xFFA12F00),
    onPrimaryContainer = Color(0xFFFFDBCC),
    secondary = Color(0xFFE6BEAA),
    onSecondary = Color(0xFF432B1E),
    secondaryContainer = Color(0xFF5D4133),
    onSecondaryContainer = Color(0xFFFFDBCC),
    tertiary = Color(0xFFD7C58D),
    onTertiary = Color(0xFF3B2F04),
    tertiaryContainer = Color(0xFF524619),
    onTertiaryContainer = Color(0xFFF4E1A7),
    error = Color(0xFFFFB4AB),
    errorContainer = Color(0xFF93000A),
    onError = Color(0xFF690005),
    onErrorContainer = Color(0xFFFFDAD6),
    background = Color(0xFF191210),
    onBackground = Color(0xFFF1E4DD),
    surface = Color(0xFF191210),
    onSurface = Color(0xFFF1E4DD),
    surfaceVariant = Color(0xFF52443C),
    onSurfaceVariant = Color(0xFFD7C2B8),
    outline = Color(0xFF9F8D84),
    inverseOnSurface = Color(0xFF191210),
    inverseSurface = Color(0xFFF1E4DD),
    inversePrimary = Color(0xFFFF6900),
    surfaceTint = Color(0xFFFFB690)
)

/**
 * Basic typography for Xiaomi theme
 */
private val XiaomiTypography = Typography()

/**
 * Basic shapes for Xiaomi theme
 */
private val XiaomiShapes = Shapes()