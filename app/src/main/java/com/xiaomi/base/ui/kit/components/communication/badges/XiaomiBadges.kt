package com.xiaomi.base.ui.kit.components.communication.badges

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart

/**
 * Xiaomi Base UI Kit Badge
 * 
 * A Material Design 3 badge component with Xiaomi design tokens.
 * Used to display small amounts of information like notification counts.
 * 
 * @param modifier Modifier to be applied to the badge
 * @param containerColor The background color of the badge
 * @param contentColor The color of the content inside the badge
 * @param content The content to be displayed inside the badge
 */
@Composable
fun XiaomiBadge(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.error,
    contentColor: Color = MaterialTheme.colorScheme.onError,
    content: (@Composable RowScope.() -> Unit)? = null
) {
    Badge(
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        content = content
    )
}

/**
 * Xiaomi Base UI Kit Badged Box
 * 
 * A container that positions a badge relative to its content.
 * 
 * @param badge The badge composable to be displayed
 * @param modifier Modifier to be applied to the badged box
 * @param content The main content that the badge will be positioned relative to
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun XiaomiBadgedBox(
    badge: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    BadgedBox(
        badge = badge,
        modifier = modifier,
        content = content
    )
}

/**
 * Xiaomi Notification Badge
 * 
 * A specialized badge for showing notification counts.
 * 
 * @param count The number to display in the badge
 * @param modifier Modifier to be applied to the badge
 * @param maxCount Maximum count to display before showing "+" (e.g., "99+")
 * @param showZero Whether to show the badge when count is 0
 * @param containerColor The background color of the badge
 * @param contentColor The color of the text inside the badge
 */
@Composable
fun XiaomiNotificationBadge(
    count: Int,
    modifier: Modifier = Modifier,
    maxCount: Int = 99,
    showZero: Boolean = false,
    containerColor: Color = MaterialTheme.colorScheme.error,
    contentColor: Color = MaterialTheme.colorScheme.onError
) {
    if (count > 0 || showZero) {
        XiaomiBadge(
            modifier = modifier,
            containerColor = containerColor,
            contentColor = contentColor
        ) {
            val displayText = when {
                count <= maxCount -> count.toString()
                else -> "$maxCount+"
            }
            Text(
                text = displayText,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp
            )
        }
    }
}

/**
 * Xiaomi Status Badge
 * 
 * A badge for showing status information with different colors.
 * 
 * @param text The text to display in the badge
 * @param status The status type that determines the color
 * @param modifier Modifier to be applied to the badge
 */
@Composable
fun XiaomiStatusBadge(
    text: String,
    status: BadgeStatus = BadgeStatus.Default,
    modifier: Modifier = Modifier
) {
    val colors = when (status) {
        BadgeStatus.Success -> Pair(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.onPrimary
        )
        BadgeStatus.Warning -> Pair(
            MaterialTheme.colorScheme.tertiary,
            MaterialTheme.colorScheme.onTertiary
        )
        BadgeStatus.Error -> Pair(
            MaterialTheme.colorScheme.error,
            MaterialTheme.colorScheme.onError
        )
        BadgeStatus.Info -> Pair(
            MaterialTheme.colorScheme.secondary,
            MaterialTheme.colorScheme.onSecondary
        )
        BadgeStatus.Default -> Pair(
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
    
    XiaomiBadge(
        modifier = modifier,
        containerColor = colors.first,
        contentColor = colors.second
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
        )
    }
}

/**
 * Xiaomi Dot Badge
 * 
 * A simple dot indicator without text content.
 * 
 * @param modifier Modifier to be applied to the badge
 * @param color The color of the dot
 * @param size The size of the dot
 */
@Composable
fun XiaomiDotBadge(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.error,
    size: androidx.compose.ui.unit.Dp = 8.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}

/**
 * Xiaomi Custom Badge
 * 
 * A customizable badge with flexible content and styling.
 * 
 * @param modifier Modifier to be applied to the badge
 * @param containerColor The background color of the badge
 * @param contentColor The color of the content inside the badge
 * @param shape The shape of the badge
 * @param content The content to be displayed inside the badge
 */
@Composable
fun XiaomiCustomBadge(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    contentColor: Color = MaterialTheme.colorScheme.onPrimary,
    shape: androidx.compose.ui.graphics.Shape = CircleShape,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .clip(shape)
            .background(containerColor)
            .sizeIn(minWidth = 16.dp, minHeight = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(
            androidx.compose.material3.LocalContentColor provides contentColor
        ) {
            content()
        }
    }
}

/**
 * Badge status types for different use cases
 */
enum class BadgeStatus {
    Default,
    Success,
    Warning,
    Error,
    Info
}

// Preview composables for design system documentation
@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "Xiaomi Badges - Light")
@Composable
fun XiaomiBadgesPreview() {
    XiaomiPreviewTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Basic badges
            Text("Basic Badges", style = MaterialTheme.typography.titleMedium)
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                XiaomiBadge {
                    Text("New")
                }
                
                XiaomiBadge {
                    Text("99+")
                }
                
                XiaomiDotBadge()
            }
            
            // Badged icons
            Text("Badged Icons", style = MaterialTheme.typography.titleMedium)
            Row(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                XiaomiBadgedBox(
                    badge = {
                        XiaomiNotificationBadge(count = 5)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                XiaomiBadgedBox(
                    badge = {
                        XiaomiNotificationBadge(count = 123)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email",
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                XiaomiBadgedBox(
                    badge = {
                        XiaomiDotBadge()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Cart",
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
            
            // Status badges
            Text("Status Badges", style = MaterialTheme.typography.titleMedium)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                XiaomiStatusBadge("Success", BadgeStatus.Success)
                XiaomiStatusBadge("Warning", BadgeStatus.Warning)
                XiaomiStatusBadge("Error", BadgeStatus.Error)
                XiaomiStatusBadge("Info", BadgeStatus.Info)
            }
            
            // Custom badges
            Text("Custom Badges", style = MaterialTheme.typography.titleMedium)
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                XiaomiCustomBadge(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "BETA",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
                
                XiaomiCustomBadge(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.onTertiary)
                        )
                        Text(
                            text = "Live",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "Xiaomi Badges - Dark")
@Composable
fun XiaomiBadgesDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                XiaomiBadgedBox(
                    badge = {
                        XiaomiNotificationBadge(count = 42)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = "Notifications",
                        modifier = Modifier.size(24.dp)
                    )
                }
                
                XiaomiStatusBadge("Online", BadgeStatus.Success)
                XiaomiStatusBadge("Offline", BadgeStatus.Error)
            }
        }
    }
}