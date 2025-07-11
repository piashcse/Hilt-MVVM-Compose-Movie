package com.xiaomi.base.ui.kit.components.specialized.security

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaomi.base.ui.kit.foundation.XiaomiTheme
import java.text.SimpleDateFormat
import java.util.*

/**
 * Xiaomi Security Components - Security and Privacy UI
 * 
 * This file contains security and privacy components following Material Design 3 principles
 * with Xiaomi's design language. These components provide comprehensive security monitoring,
 * privacy controls, and protection features.
 */

/**
 * Security Status Levels
 */
enum class SecurityStatus {
    SECURE,
    WARNING,
    VULNERABLE,
    CRITICAL
}

/**
 * Security Feature Types
 */
enum class SecurityFeatureType {
    ANTIVIRUS,
    FIREWALL,
    VPN,
    APP_LOCK,
    PRIVACY_PROTECTION,
    SECURE_FOLDER,
    DEVICE_ENCRYPTION,
    BIOMETRIC_AUTH,
    TWO_FACTOR_AUTH,
    SECURE_BOOT
}

/**
 * Privacy Permission Types
 */
enum class PrivacyPermissionType {
    CAMERA,
    MICROPHONE,
    LOCATION,
    CONTACTS,
    STORAGE,
    PHONE,
    SMS,
    CALENDAR,
    SENSORS,
    NOTIFICATIONS
}

/**
 * Data classes for security components
 */
data class SecurityFeature(
    val type: SecurityFeatureType,
    val name: String,
    val description: String,
    val isEnabled: Boolean,
    val status: SecurityStatus,
    val lastUpdated: Date? = null
)

data class SecurityThreat(
    val id: String,
    val name: String,
    val description: String,
    val severity: SecurityStatus,
    val detectedAt: Date,
    val isResolved: Boolean = false
)

data class PrivacyPermission(
    val appName: String,
    val appIcon: ImageVector,
    val permissions: List<PrivacyPermissionType>,
    val lastAccessed: Date?
)

data class SecurityScanResult(
    val totalScanned: Int,
    val threatsFound: Int,
    val vulnerabilities: Int,
    val lastScanTime: Date,
    val overallStatus: SecurityStatus
)

/**
 * Xiaomi Security Dashboard
 * Main security overview dashboard
 */
@Composable
fun XiaomiSecurityDashboard(
    securityScore: Int,
    features: List<SecurityFeature>,
    recentThreats: List<SecurityThreat>,
    scanResult: SecurityScanResult,
    modifier: Modifier = Modifier,
    onQuickScan: () -> Unit = {},
    onFullScan: () -> Unit = {},
    onFeatureToggle: (SecurityFeatureType, Boolean) -> Unit = { _, _ -> }
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Security Score
        item {
            XiaomiSecurityScoreCard(
                score = securityScore,
                status = scanResult.overallStatus,
                onQuickScan = onQuickScan,
                onFullScan = onFullScan
            )
        }
        
        // Scan Results
        item {
            XiaomiScanResultCard(scanResult = scanResult)
        }
        
        // Recent Threats
        if (recentThreats.isNotEmpty()) {
            item {
                XiaomiThreatAlertsCard(threats = recentThreats)
            }
        }
        
        // Security Features
        item {
            XiaomiSecurityFeaturesCard(
                features = features,
                onFeatureToggle = onFeatureToggle
            )
        }
    }
}

/**
 * Xiaomi Security Score Card
 * Display overall security score with actions
 */
@Composable
fun XiaomiSecurityScoreCard(
    score: Int,
    status: SecurityStatus,
    modifier: Modifier = Modifier,
    onQuickScan: () -> Unit = {},
    onFullScan: () -> Unit = {}
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = getSecurityStatusColor(status).copy(alpha = 0.1f)
        ),
        border = BorderStroke(
            width = 1.dp,
            color = getSecurityStatusColor(status).copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Security Score",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = getStatusText(status),
                        style = MaterialTheme.typography.bodyMedium,
                        color = getSecurityStatusColor(status)
                    )
                }
                
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(getSecurityStatusColor(status).copy(alpha = 0.2f))
                            .border(
                                width = 3.dp,
                                color = getSecurityStatusColor(status),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "$score",
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = getSecurityStatusColor(status)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onQuickScan,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Quick Scan",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Quick Scan")
                }
                
                Button(
                    onClick = onFullScan,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = getSecurityStatusColor(status)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Security,
                        contentDescription = "Full Scan",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Full Scan")
                }
            }
        }
    }
}

/**
 * Xiaomi Scan Result Card
 * Display latest scan results
 */
@Composable
fun XiaomiScanResultCard(
    scanResult: SecurityScanResult,
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Scanner,
                    contentDescription = "Scan Results",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Latest Scan Results",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                XiaomiScanStatItem(
                    label = "Scanned",
                    value = scanResult.totalScanned.toString(),
                    icon = Icons.Default.Folder
                )
                
                XiaomiScanStatItem(
                    label = "Threats",
                    value = scanResult.threatsFound.toString(),
                    icon = Icons.Default.Warning,
                    valueColor = if (scanResult.threatsFound > 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )
                
                XiaomiScanStatItem(
                    label = "Vulnerabilities",
                    value = scanResult.vulnerabilities.toString(),
                    icon = Icons.Default.Shield,
                    valueColor = if (scanResult.vulnerabilities > 0) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Last scan: ${SimpleDateFormat("MMM dd, yyyy HH:mm", Locale.getDefault()).format(scanResult.lastScanTime)}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

/**
 * Xiaomi Threat Alerts Card
 * Display recent security threats
 */
@Composable
fun XiaomiThreatAlertsCard(
    threats: List<SecurityThreat>,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Threats",
                    tint = MaterialTheme.colorScheme.error
                )
                Text(
                    text = "Security Alerts",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.error
                )
                
                Spacer(modifier = Modifier.weight(1f))
                
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.error
                ) {
                    Text(
                        text = threats.size.toString(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onError,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            threats.take(3).forEach { threat ->
                XiaomiThreatItem(
                    threat = threat,
                    modifier = Modifier.fillMaxWidth()
                )
                if (threat != threats.last()) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            
            if (threats.size > 3) {
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(
                    onClick = { /* Navigate to full threats list */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("View All ${threats.size} Threats")
                }
            }
        }
    }
}

/**
 * Xiaomi Security Features Card
 * Display and manage security features
 */
@Composable
fun XiaomiSecurityFeaturesCard(
    features: List<SecurityFeature>,
    modifier: Modifier = Modifier,
    onFeatureToggle: (SecurityFeatureType, Boolean) -> Unit = { _, _ -> }
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Security,
                    contentDescription = "Security Features",
                    tint = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Security Features",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            features.forEach { feature ->
                XiaomiSecurityFeatureItem(
                    feature = feature,
                    onToggle = { enabled -> onFeatureToggle(feature.type, enabled) },
                    modifier = Modifier.fillMaxWidth()
                )
                if (feature != features.last()) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

/**
 * Xiaomi Privacy Dashboard
 * Privacy permissions and controls
 */
@Composable
fun XiaomiPrivacyDashboard(
    permissions: List<PrivacyPermission>,
    modifier: Modifier = Modifier,
    onPermissionRevoke: (String, PrivacyPermissionType) -> Unit = { _, _ -> }
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Privacy Dashboard",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        items(permissions) { permission ->
            XiaomiPrivacyPermissionCard(
                permission = permission,
                onPermissionRevoke = onPermissionRevoke
            )
        }
    }
}

/**
 * Helper Composables
 */
@Composable
fun XiaomiScanStatItem(
    label: String,
    value: String,
    icon: ImageVector,
    valueColor: Color = MaterialTheme.colorScheme.primary
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = valueColor,
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = valueColor
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun XiaomiThreatItem(
    threat: SecurityThreat,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Error,
            contentDescription = "Threat",
            tint = getSecurityStatusColor(threat.severity),
            modifier = Modifier.size(20.dp)
        )
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = threat.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = threat.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        
        if (!threat.isResolved) {
            TextButton(
                onClick = { /* Handle threat resolution */ }
            ) {
                Text("Resolve")
            }
        }
    }
}

@Composable
fun XiaomiSecurityFeatureItem(
    feature: SecurityFeature,
    onToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        val icon = getSecurityFeatureIcon(feature.type)
        
        Icon(
            imageVector = icon,
            contentDescription = feature.name,
            tint = if (feature.isEnabled) getSecurityStatusColor(feature.status) else MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(24.dp)
        )
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = feature.name,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = feature.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        Switch(
            checked = feature.isEnabled,
            onCheckedChange = onToggle
        )
    }
}

@Composable
fun XiaomiPrivacyPermissionCard(
    permission: PrivacyPermission,
    modifier: Modifier = Modifier,
    onPermissionRevoke: (String, PrivacyPermissionType) -> Unit = { _, _ -> }
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
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(
                    imageVector = permission.appIcon,
                    contentDescription = permission.appName,
                    modifier = Modifier.size(32.dp)
                )
                
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = permission.appName,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "${permission.permissions.size} permissions",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                permission.lastAccessed?.let { lastAccessed ->
                    Text(
                        text = SimpleDateFormat("MMM dd", Locale.getDefault()).format(lastAccessed),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Permission chips
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                permission.permissions.take(3).forEach { permissionType ->
                    AssistChip(
                        onClick = { onPermissionRevoke(permission.appName, permissionType) },
                        label = { Text(permissionType.name.lowercase().replaceFirstChar { it.uppercase() }) },
                        leadingIcon = {
                            Icon(
                                imageVector = getPermissionIcon(permissionType),
                                contentDescription = permissionType.name,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    )
                }
                
                if (permission.permissions.size > 3) {
                    AssistChip(
                        onClick = { /* Show all permissions */ },
                        label = { Text("+${permission.permissions.size - 3}") }
                    )
                }
            }
        }
    }
}

/**
 * Helper functions
 */
@Composable
fun getSecurityStatusColor(status: SecurityStatus): Color {
    return when (status) {
        SecurityStatus.SECURE -> Color(0xFF4CAF50)
        SecurityStatus.WARNING -> Color(0xFFFF9800)
        SecurityStatus.VULNERABLE -> Color(0xFFFF5722)
        SecurityStatus.CRITICAL -> Color(0xFFF44336)
    }
}

fun getStatusText(status: SecurityStatus): String {
    return when (status) {
        SecurityStatus.SECURE -> "Your device is secure"
        SecurityStatus.WARNING -> "Some issues detected"
        SecurityStatus.VULNERABLE -> "Vulnerabilities found"
        SecurityStatus.CRITICAL -> "Immediate action required"
    }
}

fun getSecurityFeatureIcon(type: SecurityFeatureType): ImageVector {
    return when (type) {
        SecurityFeatureType.ANTIVIRUS -> Icons.Default.Security
        SecurityFeatureType.FIREWALL -> Icons.Default.Shield
        SecurityFeatureType.VPN -> Icons.Default.VpnKey
        SecurityFeatureType.APP_LOCK -> Icons.Default.Lock
        SecurityFeatureType.PRIVACY_PROTECTION -> Icons.Default.PrivacyTip
        SecurityFeatureType.SECURE_FOLDER -> Icons.Default.FolderSpecial
        SecurityFeatureType.DEVICE_ENCRYPTION -> Icons.Default.EnhancedEncryption
        SecurityFeatureType.BIOMETRIC_AUTH -> Icons.Default.Fingerprint
        SecurityFeatureType.TWO_FACTOR_AUTH -> Icons.Default.Security
        SecurityFeatureType.SECURE_BOOT -> Icons.Default.VerifiedUser
    }
}

fun getPermissionIcon(type: PrivacyPermissionType): ImageVector {
    return when (type) {
        PrivacyPermissionType.CAMERA -> Icons.Default.Camera
        PrivacyPermissionType.MICROPHONE -> Icons.Default.Mic
        PrivacyPermissionType.LOCATION -> Icons.Default.LocationOn
        PrivacyPermissionType.CONTACTS -> Icons.Default.Contacts
        PrivacyPermissionType.STORAGE -> Icons.Default.Storage
        PrivacyPermissionType.PHONE -> Icons.Default.Phone
        PrivacyPermissionType.SMS -> Icons.Default.Sms
        PrivacyPermissionType.CALENDAR -> Icons.Default.CalendarToday
        PrivacyPermissionType.SENSORS -> Icons.Default.Sensors
        PrivacyPermissionType.NOTIFICATIONS -> Icons.Default.Notifications
    }
}

// Preview Functions
@Preview(name = "Security Dashboard - Light")
@Composable
fun XiaomiSecurityDashboardPreview() {
    XiaomiTheme {
        Surface {
            val sampleFeatures = listOf(
                SecurityFeature(
                    SecurityFeatureType.ANTIVIRUS,
                    "Antivirus Protection",
                    "Real-time malware protection",
                    true,
                    SecurityStatus.SECURE
                ),
                SecurityFeature(
                    SecurityFeatureType.FIREWALL,
                    "Firewall",
                    "Network traffic monitoring",
                    true,
                    SecurityStatus.SECURE
                ),
                SecurityFeature(
                    SecurityFeatureType.APP_LOCK,
                    "App Lock",
                    "Secure your apps with biometric",
                    false,
                    SecurityStatus.WARNING
                )
            )
            
            val sampleThreats = listOf(
                SecurityThreat(
                    "1",
                    "Suspicious App Detected",
                    "Unknown app requesting sensitive permissions",
                    SecurityStatus.WARNING,
                    Date()
                )
            )
            
            val sampleScanResult = SecurityScanResult(
                totalScanned = 1247,
                threatsFound = 1,
                vulnerabilities = 0,
                lastScanTime = Date(),
                overallStatus = SecurityStatus.WARNING
            )
            
            XiaomiSecurityDashboard(
                securityScore = 85,
                features = sampleFeatures,
                recentThreats = sampleThreats,
                scanResult = sampleScanResult
            )
        }
    }
}

@Preview(name = "Privacy Dashboard - Light")
@Composable
fun XiaomiPrivacyDashboardPreview() {
    XiaomiTheme {
        Surface {
            val samplePermissions = listOf(
                PrivacyPermission(
                    "Camera App",
                    Icons.Default.CameraAlt,
                    listOf(PrivacyPermissionType.CAMERA, PrivacyPermissionType.STORAGE),
                    Date()
                ),
                PrivacyPermission(
                    "Maps",
                    Icons.Default.Map,
                    listOf(PrivacyPermissionType.LOCATION, PrivacyPermissionType.STORAGE),
                    Date()
                ),
                PrivacyPermission(
                    "Social Media",
                    Icons.Default.Share,
                    listOf(
                        PrivacyPermissionType.CAMERA,
                        PrivacyPermissionType.MICROPHONE,
                        PrivacyPermissionType.CONTACTS,
                        PrivacyPermissionType.STORAGE
                    ),
                    Date()
                )
            )
            
            XiaomiPrivacyDashboard(
                permissions = samplePermissions
            )
        }
    }
}

@Preview(name = "Security Components - Dark")
@Composable
fun XiaomiSecurityDarkPreview() {
    XiaomiTheme(darkTheme = true) {
        Surface {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text("Dark Theme Security")
                
                val sampleScanResult = SecurityScanResult(
                    totalScanned = 1247,
                    threatsFound = 0,
                    vulnerabilities = 0,
                    lastScanTime = Date(),
                    overallStatus = SecurityStatus.SECURE
                )
                
                XiaomiSecurityScoreCard(
                    score = 95,
                    status = SecurityStatus.SECURE
                )
                
                XiaomiScanResultCard(scanResult = sampleScanResult)
            }
        }
    }
}