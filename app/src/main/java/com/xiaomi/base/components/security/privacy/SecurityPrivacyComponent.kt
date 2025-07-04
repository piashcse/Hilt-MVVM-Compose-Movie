package com.xiaomi.base.components.security.privacy

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.*
import kotlin.random.Random

// Helper colors
val Orange = Color(0xFFFF9800)
val Purple = Color(0xFF9C27B0)

// Enums
enum class SecurityThreatLevel {
    LOW,
    MEDIUM,
    HIGH,
    CRITICAL
}

enum class PrivacyLevel {
    PUBLIC,
    INTERNAL,
    CONFIDENTIAL,
    RESTRICTED,
    TOP_SECRET
}

enum class EncryptionType {
    AES_128,
    AES_256,
    RSA_2048,
    RSA_4096,
    ECDSA,
    NONE
}

enum class AuthenticationMethod {
    PASSWORD,
    BIOMETRIC,
    TWO_FACTOR,
    OAUTH,
    SSO,
    CERTIFICATE
}

enum class PermissionType {
    CAMERA,
    MICROPHONE,
    LOCATION,
    STORAGE,
    CONTACTS,
    PHONE,
    SMS,
    CALENDAR,
    SENSORS,
    NETWORK
}

enum class SecurityEventType {
    LOGIN_ATTEMPT,
    PERMISSION_REQUEST,
    DATA_ACCESS,
    ENCRYPTION_EVENT,
    VULNERABILITY_SCAN,
    THREAT_DETECTION,
    PRIVACY_BREACH,
    COMPLIANCE_CHECK
}

enum class ComplianceStandard {
    GDPR,
    CCPA,
    HIPAA,
    SOX,
    PCI_DSS,
    ISO_27001,
    NIST
}

enum class DataCategory {
    PERSONAL_DATA,
    SENSITIVE_DATA,
    FINANCIAL_DATA,
    HEALTH_DATA,
    BIOMETRIC_DATA,
    LOCATION_DATA,
    COMMUNICATION_DATA,
    BEHAVIORAL_DATA
}

// Data Classes
data class SecurityThreat(
    val id: String,
    val title: String,
    val description: String,
    val level: SecurityThreatLevel,
    val category: String,
    val detectedAt: Long = System.currentTimeMillis(),
    val resolved: Boolean = false,
    val affectedComponents: List<String> = emptyList(),
    val recommendedActions: List<String> = emptyList()
)

data class PrivacyPolicy(
    val id: String,
    val title: String,
    val description: String,
    val dataTypes: List<DataCategory>,
    val purpose: String,
    val retention: String,
    val sharing: String,
    val userRights: List<String>,
    val lastUpdated: Long = System.currentTimeMillis()
)

data class PermissionRequest(
    val id: String,
    val permission: PermissionType,
    val requestedBy: String,
    val purpose: String,
    val timestamp: Long = System.currentTimeMillis(),
    val granted: Boolean = false,
    val permanent: Boolean = false,
    val justification: String = ""
)

data class EncryptionConfig(
    val id: String,
    val name: String,
    val type: EncryptionType,
    val keySize: Int,
    val algorithm: String,
    val enabled: Boolean = true,
    val dataTypes: List<DataCategory>,
    val performance: String = "Good"
)

data class AuthenticationConfig(
    val id: String,
    val method: AuthenticationMethod,
    val enabled: Boolean = true,
    val required: Boolean = false,
    val timeout: Long = 3600000, // 1 hour
    val maxAttempts: Int = 3,
    val lockoutDuration: Long = 300000 // 5 minutes
)

data class SecurityEvent(
    val id: String,
    val type: SecurityEventType,
    val title: String,
    val description: String,
    val severity: SecurityThreatLevel,
    val timestamp: Long = System.currentTimeMillis(),
    val userId: String? = null,
    val ipAddress: String? = null,
    val userAgent: String? = null,
    val metadata: Map<String, String> = emptyMap()
)

data class ComplianceCheck(
    val id: String,
    val standard: ComplianceStandard,
    val requirement: String,
    val status: String, // "Compliant", "Non-Compliant", "Partial"
    val score: Double, // 0.0 to 1.0
    val lastChecked: Long = System.currentTimeMillis(),
    val issues: List<String> = emptyList(),
    val recommendations: List<String> = emptyList()
)

data class DataAuditEntry(
    val id: String,
    val dataType: DataCategory,
    val action: String, // "Created", "Read", "Updated", "Deleted", "Shared"
    val userId: String,
    val timestamp: Long = System.currentTimeMillis(),
    val details: String = "",
    val ipAddress: String? = null
)

data class SecurityPrivacyConfig(
    val enableRealTimeMonitoring: Boolean = true,
    val enableThreatDetection: Boolean = true,
    val enablePrivacyScanning: Boolean = true,
    val enableComplianceChecks: Boolean = true,
    val enableAuditLogging: Boolean = true,
    val retentionDays: Int = 365,
    val alertThresholds: Map<SecurityThreatLevel, Int> = emptyMap()
)

// Main Component
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityPrivacyComponent(
    modifier: Modifier = Modifier,
    config: SecurityPrivacyConfig = SecurityPrivacyConfig()
) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Overview", "Threats", "Privacy", "Permissions", "Encryption", "Compliance", "Audit")
    
    // Sample data
    val threats by remember { mutableStateOf<List<SecurityThreat>>(generateSampleThreats()) }
    val permissions by remember { mutableStateOf<List<PermissionRequest>>(generateSamplePermissions()) }
    val encryptionConfigs by remember { mutableStateOf<List<EncryptionConfig>>(generateSampleEncryption()) }
    val complianceChecks by remember { mutableStateOf<List<ComplianceCheck>>(generateSampleCompliance()) }
    val auditEntries by remember { mutableStateOf<List<DataAuditEntry>>(generateSampleAudit()) }
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Header
        SecurityPrivacyHeader(config = config)
        
        // Tab Row
        TabRow(
            selectedTabIndex = selectedTab,
            modifier = Modifier.fillMaxWidth()
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) }
                )
            }
        }
        
        // Content
        when (selectedTab) {
            0 -> SecurityOverviewTab(threats = threats, permissions = permissions)
            1 -> SecurityThreatsTab(threats = threats)
            2 -> PrivacyTab(permissions = permissions)
            3 -> PermissionsTab(permissions = permissions, onPermissionAction = { _, _ -> })
            4 -> EncryptionTab(encryptionConfigs = encryptionConfigs)
            5 -> ComplianceTab(complianceChecks = complianceChecks)
            6 -> AuditTab(auditEntries = auditEntries)
        }
    }
}

@Composable
fun SecurityPrivacyHeader(
    config: SecurityPrivacyConfig
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Security & Privacy",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = "Comprehensive security monitoring and privacy protection",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Quick stats
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                QuickStatCard(title = "Threats", value = "3", color = Color.Red)
                QuickStatCard(title = "Permissions", value = "12", color = Orange)
                QuickStatCard(title = "Encryption", value = "Active", color = Color.Green)
                QuickStatCard(title = "Compliance", value = "94%", color = Color.Blue)
            }
        }
    }
}

@Composable
fun QuickStatCard(
    title: String,
    value: String,
    color: Color
) {
    Card(
        modifier = Modifier.size(80.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = color
            )
            Text(
                text = title,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// Tab Components
@Composable
fun SecurityOverviewTab(
    threats: List<SecurityThreat>,
    permissions: List<PermissionRequest>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Security Score: ${calculateSecurityScore(threats)}%",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Recent Threats",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    threats.filter { !it.resolved }.take(3).forEach { threat ->
                        ThreatRow(threat = threat)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
        
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Permission Requests",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    permissions.filter { !it.granted }.take(3).forEach { permission ->
                        PermissionRow(permission = permission)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun SecurityThreatsTab(threats: List<SecurityThreat>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(threats) { threat ->
            ThreatCard(threat = threat)
        }
    }
}

@Composable
fun PrivacyTab(permissions: List<PermissionRequest>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = "Privacy Protection",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }
        
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Data Collection",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "We collect only essential data needed for app functionality.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
        
        item {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Data Sharing",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Your data is never shared with third parties without consent.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun PermissionsTab(
    permissions: List<PermissionRequest>,
    onPermissionAction: (PermissionRequest, String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(permissions) { permission ->
            PermissionCard(
                permission = permission,
                onAction = { action -> onPermissionAction(permission, action) }
            )
        }
    }
}

@Composable
fun EncryptionTab(encryptionConfigs: List<EncryptionConfig>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(encryptionConfigs) { config ->
            EncryptionCard(config = config)
        }
    }
}

@Composable
fun ComplianceTab(complianceChecks: List<ComplianceCheck>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(complianceChecks) { check ->
            ComplianceCard(check = check)
        }
    }
}

@Composable
fun AuditTab(auditEntries: List<DataAuditEntry>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(auditEntries) { entry ->
            AuditEntryCard(entry = entry)
        }
    }
}

// Card Components
@Composable
fun ThreatCard(threat: SecurityThreat) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = getThreatLevelColor(threat.level).copy(alpha = 0.1f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = threat.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                AssistChip(
                    onClick = { },
                    label = { Text(threat.level.name) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = getThreatLevelColor(threat.level)
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = threat.description,
                style = MaterialTheme.typography.bodyMedium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Category: ${threat.category}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun PermissionCard(
    permission: PermissionRequest,
    onAction: (String) -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = permission.permission.name.replace("_", " "),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Requested by: ${permission.requestedBy}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                if (!permission.granted) {
                    Row {
                        Button(
                            onClick = { onAction("grant") },
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text("Grant")
                        }
                        
                        OutlinedButton(
                            onClick = { onAction("deny") }
                        ) {
                            Text("Deny")
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Purpose: ${permission.purpose}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
fun EncryptionCard(config: EncryptionConfig) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = config.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Switch(
                    checked = config.enabled,
                    onCheckedChange = { }
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Type: ${config.type.name}",
                style = MaterialTheme.typography.bodyMedium
            )
            
            Text(
                text = "Key Size: ${config.keySize} bits",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun ComplianceCard(check: ComplianceCheck) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = check.standard.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = "${(check.score * 100).toInt()}%",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = getComplianceColor(check.score)
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = check.requirement,
                style = MaterialTheme.typography.bodyMedium
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            LinearProgressIndicator(
                progress = check.score.toFloat(),
                modifier = Modifier.fillMaxWidth(),
                color = getComplianceColor(check.score)
            )
        }
    }
}

@Composable
fun AuditEntryCard(entry: DataAuditEntry) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = entry.action,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = formatTimestamp(entry.timestamp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = "Data Type: ${entry.dataType.name.replace("_", " ")}",
                style = MaterialTheme.typography.bodyMedium
            )
            
            Text(
                text = "User: ${entry.userId}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// Row Components
@Composable
fun ThreatRow(threat: SecurityThreat) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = getThreatIcon(threat.level),
            contentDescription = null,
            tint = getThreatLevelColor(threat.level),
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = threat.title,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = formatTimestamp(threat.detectedAt),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun PermissionRow(permission: PermissionRequest) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = getPermissionIcon(permission.permission),
            contentDescription = null,
            modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = permission.permission.name.replace("_", " "),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = permission.requestedBy,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// Helper Functions
fun getThreatLevelColor(level: SecurityThreatLevel): Color {
    return when (level) {
        SecurityThreatLevel.LOW -> Color.Green
        SecurityThreatLevel.MEDIUM -> Color.Yellow
        SecurityThreatLevel.HIGH -> Orange
        SecurityThreatLevel.CRITICAL -> Color.Red
    }
}

fun getThreatIcon(level: SecurityThreatLevel): androidx.compose.ui.graphics.vector.ImageVector {
    return when (level) {
        SecurityThreatLevel.LOW -> Icons.Default.Info
        SecurityThreatLevel.MEDIUM -> Icons.Default.Warning
        SecurityThreatLevel.HIGH -> Icons.Default.Error
        SecurityThreatLevel.CRITICAL -> Icons.Default.ErrorOutline
    }
}

fun getPermissionIcon(permission: PermissionType): androidx.compose.ui.graphics.vector.ImageVector {
    return when (permission) {
        PermissionType.CAMERA -> Icons.Default.Camera
        PermissionType.MICROPHONE -> Icons.Default.Mic
        PermissionType.LOCATION -> Icons.Default.LocationOn
        PermissionType.STORAGE -> Icons.Default.Storage
        PermissionType.CONTACTS -> Icons.Default.Contacts
        PermissionType.PHONE -> Icons.Default.Phone
        PermissionType.SMS -> Icons.Default.Sms
        PermissionType.CALENDAR -> Icons.Default.CalendarToday
        PermissionType.SENSORS -> Icons.Default.Sensors
        PermissionType.NETWORK -> Icons.Default.Wifi
    }
}

fun getComplianceColor(score: Double): Color {
    return when {
        score >= 0.9 -> Color.Green
        score >= 0.7 -> Color.Yellow
        score >= 0.5 -> Orange
        else -> Color.Red
    }
}

fun calculateSecurityScore(threats: List<SecurityThreat>): Int {
    val unresolvedThreats = threats.count { !it.resolved }
    val totalThreats = threats.size
    
    return if (totalThreats > 0) {
        ((totalThreats - unresolvedThreats).toDouble() / totalThreats * 100).toInt()
    } else {
        100
    }
}

fun formatTimestamp(timestamp: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - timestamp
    
    return when {
        diff < 60000 -> "Just now"
        diff < 3600000 -> "${diff / 60000}m ago"
        diff < 86400000 -> "${diff / 3600000}h ago"
        else -> "${diff / 86400000}d ago"
    }
}

// Sample Data Generators
fun generateSampleThreats(): List<SecurityThreat> {
    return listOf(
        SecurityThreat(
            id = "threat1",
            title = "Suspicious Login Attempt",
            description = "Multiple failed login attempts from unknown IP address",
            level = SecurityThreatLevel.MEDIUM,
            category = "Authentication",
            resolved = false
        ),
        SecurityThreat(
            id = "threat2",
            title = "Weak Password Detected",
            description = "User account uses weak password that can be easily compromised",
            level = SecurityThreatLevel.LOW,
            category = "Password Security",
            resolved = false
        ),
        SecurityThreat(
            id = "threat3",
            title = "Data Breach Attempt",
            description = "Unauthorized access attempt to sensitive data",
            level = SecurityThreatLevel.HIGH,
            category = "Data Security",
            resolved = true
        )
    )
}

fun generateSamplePermissions(): List<PermissionRequest> {
    return listOf(
        PermissionRequest(
            id = "perm1",
            permission = PermissionType.CAMERA,
            requestedBy = "Photo Editor App",
            purpose = "Take photos and record videos",
            granted = false
        ),
        PermissionRequest(
            id = "perm2",
            permission = PermissionType.LOCATION,
            requestedBy = "Weather App",
            purpose = "Show local weather information",
            granted = true
        ),
        PermissionRequest(
            id = "perm3",
            permission = PermissionType.MICROPHONE,
            requestedBy = "Voice Recorder",
            purpose = "Record audio notes",
            granted = false
        )
    )
}

fun generateSampleEncryption(): List<EncryptionConfig> {
    return listOf(
        EncryptionConfig(
            id = "enc1",
            name = "Database Encryption",
            type = EncryptionType.AES_256,
            keySize = 256,
            algorithm = "AES-256-GCM",
            enabled = true,
            dataTypes = listOf(DataCategory.PERSONAL_DATA, DataCategory.SENSITIVE_DATA)
        ),
        EncryptionConfig(
            id = "enc2",
            name = "File Storage Encryption",
            type = EncryptionType.AES_128,
            keySize = 128,
            algorithm = "AES-128-CBC",
            enabled = true,
            dataTypes = listOf(DataCategory.FINANCIAL_DATA, DataCategory.HEALTH_DATA)
        )
    )
}

fun generateSampleCompliance(): List<ComplianceCheck> {
    return listOf(
        ComplianceCheck(
            id = "comp1",
            standard = ComplianceStandard.GDPR,
            requirement = "Data Protection and Privacy Rights",
            status = "Compliant",
            score = 0.94
        ),
        ComplianceCheck(
            id = "comp2",
            standard = ComplianceStandard.CCPA,
            requirement = "Consumer Privacy Rights",
            status = "Partial",
            score = 0.78
        ),
        ComplianceCheck(
            id = "comp3",
            standard = ComplianceStandard.ISO_27001,
            requirement = "Information Security Management",
            status = "Compliant",
            score = 0.91
        )
    )
}

fun generateSampleAudit(): List<DataAuditEntry> {
    return listOf(
        DataAuditEntry(
            id = "audit1",
            dataType = DataCategory.PERSONAL_DATA,
            action = "Read",
            userId = "user123",
            details = "User profile accessed"
        ),
        DataAuditEntry(
            id = "audit2",
            dataType = DataCategory.FINANCIAL_DATA,
            action = "Updated",
            userId = "admin456",
            details = "Payment information modified"
        ),
        DataAuditEntry(
            id = "audit3",
            dataType = DataCategory.LOCATION_DATA,
            action = "Created",
            userId = "user789",
            details = "Location data recorded"
        )
    )
}