package com.xiaomi.base.ui.components

import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.FragmentActivity
import com.xiaomi.base.ui.theme.*
import java.util.concurrent.Executor

// Biometric Authentication Components
@Composable
fun BiometricAuthCard(
    onAuthenticationRequested: () -> Unit = {},
    onSettingsClicked: () -> Unit = {},
    isAuthenticationEnabled: Boolean = true,
    lastAuthenticationTime: String? = null,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Fingerprint,
                    contentDescription = "Biometric Authentication",
                    tint = if (isAuthenticationEnabled) Blue else Color.Gray,
                    modifier = Modifier.size(32.dp)
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Biometric Authentication",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold
                    )
                    
                    if (lastAuthenticationTime != null) {
                        Text(
                            text = "Last authenticated: $lastAuthenticationTime",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                IconButton(onClick = onSettingsClicked) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Settings",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onAuthenticationRequested,
                    enabled = isAuthenticationEnabled,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Fingerprint,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Authenticate")
                }
                
                OutlinedButton(
                    onClick = onSettingsClicked,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Settings")
                }
            }
        }
    }
}

@Composable
fun BiometricStatusCard(
    fingerprintEnabled: Boolean = true,
    faceUnlockEnabled: Boolean = false,
    voiceRecognitionEnabled: Boolean = false,
    onToggleFingerprint: (Boolean) -> Unit = {},
    onToggleFaceUnlock: (Boolean) -> Unit = {},
    onToggleVoiceRecognition: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Biometric Methods",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            BiometricMethodRow(
                icon = Icons.Default.Fingerprint,
                title = "Fingerprint",
                description = "Use fingerprint to unlock",
                enabled = fingerprintEnabled,
                onToggle = onToggleFingerprint
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            BiometricMethodRow(
                icon = Icons.Default.Face,
                title = "Face Unlock",
                description = "Use face recognition to unlock",
                enabled = faceUnlockEnabled,
                onToggle = onToggleFaceUnlock
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            BiometricMethodRow(
                icon = Icons.Default.RecordVoiceOver,
                title = "Voice Recognition",
                description = "Use voice to unlock",
                enabled = voiceRecognitionEnabled,
                onToggle = onToggleVoiceRecognition
            )
        }
    }
}

@Composable
fun BiometricMethodRow(
    icon: ImageVector,
    title: String,
    description: String,
    enabled: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = if (enabled) Blue.copy(alpha = 0.1f) else Color.Gray.copy(alpha = 0.1f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (enabled) Blue else Color.Gray,
                modifier = Modifier.size(20.dp)
            )
        }
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Switch(
            checked = enabled,
            onCheckedChange = onToggle
        )
    }
}

@Composable
fun BiometricSecurityCard(
    securityLevel: String = "High",
    encryptionEnabled: Boolean = true,
    fallbackEnabled: Boolean = true,
    onSecurityLevelChange: (String) -> Unit = {},
    onToggleEncryption: (Boolean) -> Unit = {},
    onToggleFallback: (Boolean) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Security Settings",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Security Level
            Text(
                text = "Security Level",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val securityLevels = listOf("Low", "Medium", "High")
                securityLevels.forEach { level ->
                    SecurityLevelChip(
                        level = level,
                        selected = level == securityLevel,
                        onClick = { onSecurityLevelChange(level) }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Encryption Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Hardware Encryption",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Encrypt biometric data using hardware security",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = encryptionEnabled,
                    onCheckedChange = onToggleEncryption
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Fallback Toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "PIN/Password Fallback",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "Allow PIN or password as backup authentication",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Switch(
                    checked = fallbackEnabled,
                    onCheckedChange = onToggleFallback
                )
            }
        }
    }
}

@Composable
fun SecurityLevelChip(
    level: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = when {
        selected && level == "High" -> Green.copy(alpha = 0.1f)
        selected && level == "Medium" -> Orange.copy(alpha = 0.1f)
        selected && level == "Low" -> Red.copy(alpha = 0.1f)
        else -> Color.Transparent
    }
    
    val borderColor = when {
        selected && level == "High" -> Green
        selected && level == "Medium" -> Orange
        selected && level == "Low" -> Red
        else -> Color.Gray.copy(alpha = 0.3f)
    }
    
    val textColor = when {
        selected && level == "High" -> Green
        selected && level == "Medium" -> Orange
        selected && level == "Low" -> Red
        else -> MaterialTheme.colorScheme.onSurface
    }
    
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .border(1.dp, borderColor, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 6.dp)
    ) {
        Text(
            text = level,
            fontSize = 12.sp,
            color = textColor,
            fontWeight = if (selected) FontWeight.Medium else FontWeight.Normal
        )
    }
}

// Biometric Prompt Helper Class
class BiometricAuthenticationHelper(
    private val activity: FragmentActivity,
    private val executor: Executor
) {
    
    private var biometricPrompt: BiometricPrompt? = null
    
    fun setupBiometricPrompt(
        onAuthenticationSucceeded: (BiometricPrompt.AuthenticationResult) -> Unit,
        onAuthenticationError: (Int, CharSequence) -> Unit,
        onAuthenticationFailed: () -> Unit
    ) {
        val authenticationCallback = object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                onAuthenticationSucceeded(result)
            }
            
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                onAuthenticationError(errorCode, errString)
            }
            
            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                onAuthenticationFailed()
            }
        }
        
        biometricPrompt = BiometricPrompt(activity as androidx.fragment.app.FragmentActivity, executor, authenticationCallback)
    }
    
    fun authenticate(
        title: String = "Biometric Authentication",
        subtitle: String = "Use your biometric credential to authenticate",
        description: String = "Place your finger on the sensor or look at the camera",
        negativeButtonText: String = "Cancel"
    ) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setDescription(description)
            .setNegativeButtonText(negativeButtonText)
            .build()
        
        biometricPrompt?.authenticate(promptInfo)
    }
    
    fun authenticateWithCrypto(
        cryptoObject: BiometricPrompt.CryptoObject,
        title: String = "Biometric Authentication",
        subtitle: String = "Use your biometric credential to authenticate",
        description: String = "Place your finger on the sensor or look at the camera",
        negativeButtonText: String = "Cancel"
    ) {
        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(title)
            .setSubtitle(subtitle)
            .setDescription(description)
            .setNegativeButtonText(negativeButtonText)
            .build()
        
        biometricPrompt?.authenticate(promptInfo, cryptoObject)
    }
}

// Biometric Status Indicators
@Composable
fun BiometricStatusIndicator(
    isAvailable: Boolean,
    isEnrolled: Boolean,
    modifier: Modifier = Modifier
) {
    val (color, icon, text) = when {
        !isAvailable -> Triple(Color.Gray, Icons.Default.Block, "Not Available")
        !isEnrolled -> Triple(Orange, Icons.Default.Warning, "Not Enrolled")
        else -> Triple(Green, Icons.Default.CheckCircle, "Ready")
    }
    
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = text,
            tint = color,
            modifier = Modifier.size(16.dp)
        )
        
        Spacer(modifier = Modifier.width(4.dp))
        
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = color,
            fontSize = 12.sp
        )
    }
}

@Composable
fun BiometricEnrollmentCard(
    onEnrollFingerprint: () -> Unit = {},
    onEnrollFace: () -> Unit = {},
    fingerprintEnrolled: Boolean = false,
    faceEnrolled: Boolean = false,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Biometric Enrollment",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Fingerprint Enrollment
            EnrollmentMethodCard(
                icon = Icons.Default.Fingerprint,
                title = "Fingerprint",
                description = "Add your fingerprint for quick and secure access",
                enrolled = fingerprintEnrolled,
                onEnroll = onEnrollFingerprint
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Face Enrollment
            EnrollmentMethodCard(
                icon = Icons.Default.Face,
                title = "Face Recognition",
                description = "Set up face unlock for hands-free authentication",
                enrolled = faceEnrolled,
                onEnroll = onEnrollFace
            )
        }
    }
}

@Composable
fun EnrollmentMethodCard(
    icon: ImageVector,
    title: String,
    description: String,
    enrolled: Boolean,
    onEnroll: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = if (enrolled) Green.copy(alpha = 0.05f) else MaterialTheme.colorScheme.surface
        ),
        border = if (enrolled) {
            androidx.compose.foundation.BorderStroke(1.dp, Green.copy(alpha = 0.3f))
        } else null
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        color = if (enrolled) Green.copy(alpha = 0.1f) else Blue.copy(alpha = 0.1f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (enrolled) Icons.Default.CheckCircle else icon,
                    contentDescription = title,
                    tint = if (enrolled) Green else Blue,
                    modifier = Modifier.size(20.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = if (enrolled) "Enrolled successfully" else description,
                    style = MaterialTheme.typography.bodySmall,
                    color = if (enrolled) Green else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            if (!enrolled) {
                Button(
                    onClick = onEnroll,
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(
                        text = "Enroll",
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}