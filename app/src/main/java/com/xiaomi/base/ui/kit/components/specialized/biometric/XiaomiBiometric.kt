package com.xiaomi.base.ui.kit.components.specialized.biometric

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaomi.base.ui.kit.foundation.XiaomiTheme

/**
 * Xiaomi Biometric Components - Biometric Authentication UI
 * 
 * This file contains biometric authentication components following Material Design 3 principles
 * with Xiaomi's design language. These components provide secure and user-friendly
 * biometric authentication interfaces.
 */

/**
 * Biometric Authentication States
 */
enum class BiometricState {
    IDLE,
    SCANNING,
    SUCCESS,
    ERROR,
    UNAVAILABLE
}

/**
 * Biometric Authentication Types
 */
enum class BiometricType {
    FINGERPRINT,
    FACE_ID,
    VOICE,
    IRIS
}

/**
 * Data class for biometric authentication result
 */
data class BiometricResult(
    val isSuccess: Boolean,
    val errorMessage: String? = null,
    val biometricType: BiometricType
)

/**
 * Xiaomi Biometric Prompt
 * Main biometric authentication dialog
 */
@Composable
fun XiaomiBiometricPrompt(
    title: String,
    subtitle: String? = null,
    description: String? = null,
    biometricType: BiometricType = BiometricType.FINGERPRINT,
    state: BiometricState = BiometricState.IDLE,
    onAuthenticate: () -> Unit,
    onCancel: () -> Unit,
    onUsePassword: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center
            )
            
            if (subtitle != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Biometric Icon
            XiaomiBiometricIcon(
                biometricType = biometricType,
                state = state,
                modifier = Modifier.size(80.dp)
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Description
            if (description != null) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // State Message
            XiaomiBiometricStateMessage(
                state = state,
                biometricType = biometricType
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            // Action Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Cancel Button
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }
                
                // Use Password Button (if available)
                if (onUsePassword != null) {
                    OutlinedButton(
                        onClick = onUsePassword,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Use Password")
                    }
                }
                
                // Try Again Button (for error state)
                if (state == BiometricState.ERROR) {
                    Button(
                        onClick = onAuthenticate,
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Try Again")
                    }
                }
            }
        }
    }
}

/**
 * Xiaomi Biometric Icon
 * Animated icon based on biometric type and state
 */
@Composable
fun XiaomiBiometricIcon(
    biometricType: BiometricType,
    state: BiometricState,
    modifier: Modifier = Modifier
) {
    val icon = when (biometricType) {
        BiometricType.FINGERPRINT -> Icons.Default.Fingerprint
        BiometricType.FACE_ID -> Icons.Default.Face
        BiometricType.VOICE -> Icons.Default.Mic
        BiometricType.IRIS -> Icons.Default.RemoveRedEye
    }
    
    val (iconColor, backgroundColor) = when (state) {
        BiometricState.IDLE -> MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.primaryContainer
        BiometricState.SCANNING -> MaterialTheme.colorScheme.primary to MaterialTheme.colorScheme.primaryContainer
        BiometricState.SUCCESS -> Color(0xFF4CAF50) to Color(0xFFE8F5E8)
        BiometricState.ERROR -> MaterialTheme.colorScheme.error to MaterialTheme.colorScheme.errorContainer
        BiometricState.UNAVAILABLE -> MaterialTheme.colorScheme.onSurfaceVariant to MaterialTheme.colorScheme.surfaceVariant
    }
    
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(backgroundColor)
            .border(
                width = if (state == BiometricState.SCANNING) 2.dp else 0.dp,
                color = iconColor,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "${biometricType.name} Authentication",
            tint = iconColor,
            modifier = Modifier.size(40.dp)
        )
    }
}

/**
 * Xiaomi Biometric State Message
 * Display appropriate message based on current state
 */
@Composable
fun XiaomiBiometricStateMessage(
    state: BiometricState,
    biometricType: BiometricType,
    modifier: Modifier = Modifier
) {
    val message = when (state) {
        BiometricState.IDLE -> when (biometricType) {
            BiometricType.FINGERPRINT -> "Touch the fingerprint sensor"
            BiometricType.FACE_ID -> "Look at the camera"
            BiometricType.VOICE -> "Speak your passphrase"
            BiometricType.IRIS -> "Look at the iris scanner"
        }
        BiometricState.SCANNING -> "Scanning..."
        BiometricState.SUCCESS -> "Authentication successful"
        BiometricState.ERROR -> "Authentication failed. Please try again."
        BiometricState.UNAVAILABLE -> "Biometric authentication is not available"
    }
    
    val textColor = when (state) {
        BiometricState.SUCCESS -> Color(0xFF4CAF50)
        BiometricState.ERROR -> MaterialTheme.colorScheme.error
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    
    Text(
        text = message,
        style = MaterialTheme.typography.bodyMedium,
        color = textColor,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

/**
 * Xiaomi Biometric Settings Card
 * Settings card for biometric configuration
 */
@Composable
fun XiaomiBiometricSettingsCard(
    title: String,
    description: String,
    biometricType: BiometricType,
    isEnabled: Boolean,
    isAvailable: Boolean = true,
    onToggle: (Boolean) -> Unit,
    onConfigure: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    XiaomiBiometricIcon(
                        biometricType = biometricType,
                        state = if (isAvailable) BiometricState.IDLE else BiometricState.UNAVAILABLE,
                        modifier = Modifier.size(40.dp)
                    )
                    
                    Column {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                Switch(
                    checked = isEnabled && isAvailable,
                    onCheckedChange = onToggle,
                    enabled = isAvailable
                )
            }
            
            if (onConfigure != null && isEnabled && isAvailable) {
                Spacer(modifier = Modifier.height(12.dp))
                TextButton(
                    onClick = onConfigure,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Configure ${biometricType.name.lowercase().replaceFirstChar { it.uppercase() }}")
                }
            }
        }
    }
}

/**
 * Xiaomi Quick Biometric Button
 * Compact biometric authentication button
 */
@Composable
fun XiaomiQuickBiometricButton(
    biometricType: BiometricType,
    state: BiometricState = BiometricState.IDLE,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    val icon = when (biometricType) {
        BiometricType.FINGERPRINT -> Icons.Default.Fingerprint
        BiometricType.FACE_ID -> Icons.Default.Face
        BiometricType.VOICE -> Icons.Default.Mic
        BiometricType.IRIS -> Icons.Default.RemoveRedEye
    }
    
    IconButton(
        onClick = onClick,
        enabled = enabled && state != BiometricState.UNAVAILABLE,
        modifier = modifier
    ) {
        XiaomiBiometricIcon(
            biometricType = biometricType,
            state = state,
            modifier = Modifier.size(32.dp)
        )
    }
}

/**
 * Xiaomi Biometric Status Indicator
 * Small status indicator for biometric availability
 */
@Composable
fun XiaomiBiometricStatusIndicator(
    biometricTypes: List<BiometricType>,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Security,
            contentDescription = "Security",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(16.dp)
        )
        
        Text(
            text = "${biometricTypes.size} biometric method${if (biometricTypes.size > 1) "s" else ""} available",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            biometricTypes.forEach { type ->
                val icon = when (type) {
                    BiometricType.FINGERPRINT -> Icons.Default.Fingerprint
                    BiometricType.FACE_ID -> Icons.Default.Face
                    BiometricType.VOICE -> Icons.Default.Mic
                    BiometricType.IRIS -> Icons.Default.RemoveRedEye
                }
                
                Icon(
                    imageVector = icon,
                    contentDescription = type.name,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(12.dp)
                )
            }
        }
    }
}

// Preview Functions
@Preview(name = "Biometric Prompt - Light")
@Composable
fun XiaomiBiometricPromptPreview() {
    XiaomiTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                XiaomiBiometricPrompt(
                    title = "Authenticate",
                    subtitle = "Use your fingerprint to continue",
                    description = "Place your finger on the sensor to verify your identity",
                    biometricType = BiometricType.FINGERPRINT,
                    state = BiometricState.IDLE,
                    onAuthenticate = {},
                    onCancel = {},
                    onUsePassword = {}
                )
            }
        }
    }
}

@Preview(name = "Biometric States - Light")
@Composable
fun XiaomiBiometricStatesPreview() {
    XiaomiTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Biometric States")
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        XiaomiBiometricIcon(
                            biometricType = BiometricType.FINGERPRINT,
                            state = BiometricState.IDLE
                        )
                        Text("Idle", style = MaterialTheme.typography.bodySmall)
                    }
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        XiaomiBiometricIcon(
                            biometricType = BiometricType.FINGERPRINT,
                            state = BiometricState.SCANNING
                        )
                        Text("Scanning", style = MaterialTheme.typography.bodySmall)
                    }
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        XiaomiBiometricIcon(
                            biometricType = BiometricType.FINGERPRINT,
                            state = BiometricState.SUCCESS
                        )
                        Text("Success", style = MaterialTheme.typography.bodySmall)
                    }
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        XiaomiBiometricIcon(
                            biometricType = BiometricType.FINGERPRINT,
                            state = BiometricState.ERROR
                        )
                        Text("Error", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Preview(name = "Biometric Types - Light")
@Composable
fun XiaomiBiometricTypesPreview() {
    XiaomiTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Biometric Types")
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        XiaomiBiometricIcon(
                            biometricType = BiometricType.FINGERPRINT,
                            state = BiometricState.IDLE
                        )
                        Text("Fingerprint", style = MaterialTheme.typography.bodySmall)
                    }
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        XiaomiBiometricIcon(
                            biometricType = BiometricType.FACE_ID,
                            state = BiometricState.IDLE
                        )
                        Text("Face ID", style = MaterialTheme.typography.bodySmall)
                    }
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        XiaomiBiometricIcon(
                            biometricType = BiometricType.VOICE,
                            state = BiometricState.IDLE
                        )
                        Text("Voice", style = MaterialTheme.typography.bodySmall)
                    }
                    
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        XiaomiBiometricIcon(
                            biometricType = BiometricType.IRIS,
                            state = BiometricState.IDLE
                        )
                        Text("Iris", style = MaterialTheme.typography.bodySmall)
                    }
                }
            }
        }
    }
}

@Preview(name = "Biometric Settings - Light")
@Composable
fun XiaomiBiometricSettingsPreview() {
    XiaomiTheme {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Biometric Settings")
                
                XiaomiBiometricSettingsCard(
                    title = "Fingerprint",
                    description = "Use your fingerprint to unlock and authenticate",
                    biometricType = BiometricType.FINGERPRINT,
                    isEnabled = true,
                    isAvailable = true,
                    onToggle = {},
                    onConfigure = {}
                )
                
                XiaomiBiometricSettingsCard(
                    title = "Face Recognition",
                    description = "Use your face to unlock and authenticate",
                    biometricType = BiometricType.FACE_ID,
                    isEnabled = false,
                    isAvailable = true,
                    onToggle = {},
                    onConfigure = {}
                )
                
                XiaomiBiometricSettingsCard(
                    title = "Voice Recognition",
                    description = "Use your voice to authenticate",
                    biometricType = BiometricType.VOICE,
                    isEnabled = false,
                    isAvailable = false,
                    onToggle = {}
                )
            }
        }
    }
}

@Preview(name = "Biometric Components - Dark")
@Composable
fun XiaomiBiometricDarkPreview() {
    XiaomiTheme(darkTheme = true) {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Dark Theme Biometric")
                
                XiaomiBiometricStatusIndicator(
                    biometricTypes = listOf(
                        BiometricType.FINGERPRINT,
                        BiometricType.FACE_ID
                    )
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    XiaomiQuickBiometricButton(
                        biometricType = BiometricType.FINGERPRINT,
                        onClick = {}
                    )
                    
                    XiaomiQuickBiometricButton(
                        biometricType = BiometricType.FACE_ID,
                        onClick = {}
                    )
                }
            }
        }
    }
}