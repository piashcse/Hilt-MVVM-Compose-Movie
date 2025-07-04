package com.xiaomi.base.ui.components

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaomi.base.ui.theme.*
import com.xiaomi.base.utils.*
import kotlin.random.Random

// Security Color Constants
private val SecurityCritical = Color(0xFFD32F2F)  // Red
private val SecurityHigh = Color(0xFFFF6F00)       // Orange  
private val SecurityMedium = Color(0xFFFBC02D)     // Yellow
private val SecurityLow = Color(0xFF388E3C)        // Green

// Security Threat Components
@Composable
fun ThreatLevelDistributionCard(
    threats: Map<String, Int> = mapOf(
        "Critical" to 2,
        "High" to 5,
        "Medium" to 12,
        "Low" to 8
    ),
    onThreatClick: (String) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Threat Level Distribution",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            threats.forEach { (level, count) ->
                ThreatLevelItem(
                    level = level,
                    count = count,
                    onClick = { onThreatClick(level) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun ThreatLevelItem(
    level: String,
    count: Int,
    onClick: () -> Unit
) {
    val color = when (level.lowercase()) {
        "critical" -> SecurityCritical
        "high" -> SecurityHigh
        "medium" -> SecurityMedium
        "low" -> SecurityLow
        else -> Color.Gray
    }
    
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(color, CircleShape)
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Text(
            text = level,
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium
        )
        
        Surface(
            color = color.copy(alpha = 0.1f),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = count.toString(),
                color = color,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun ComplianceStatusCard(
    standards: List<Map<String, Any>> = listOf(
        mapOf("name" to "GDPR", "status" to "compliant", "score" to 95),
        mapOf("name" to "HIPAA", "status" to "partial", "score" to 78),
        mapOf("name" to "SOC 2", "status" to "non-compliant", "score" to 45),
        mapOf("name" to "ISO 27001", "status" to "compliant", "score" to 92)
    ),
    onStandardClick: (String) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Compliance Status",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(standards) { standard ->
                    ComplianceStandardItem(
                        standard = standard,
                        onClick = { onStandardClick(standard["name"] as? String ?: "") }
                    )
                }
            }
        }
    }
}

@Composable
fun ComplianceStandardItem(
    standard: Map<String, Any>,
    onClick: () -> Unit
) {
    val status = standard["status"] as? String ?: "unknown"
    val score = standard["score"] as? Int ?: 0
    val name = standard["name"] as? String ?: "Unknown"
    
    val (statusColor, statusText) = when (status.lowercase()) {
        "compliant" -> Green to "Compliant"
        "partial" -> Orange to "Partial"
        "non-compliant" -> Red to "Non-Compliant"
        else -> Color.Gray to "Unknown"
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = when (status.lowercase()) {
                    "compliant" -> Icons.Default.CheckCircle
                    "partial" -> Icons.Default.Warning
                    "non-compliant" -> Icons.Default.Error
                    else -> Icons.Default.Help
                },
                contentDescription = null,
                tint = statusColor
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "Score: $score%",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Surface(
                color = statusColor.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = statusText,
                    color = statusColor,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun ThreatFilterCard(
    selectedFilters: Set<String> = emptySet(),
    onFilterChange: (String, Boolean) -> Unit = { _, _ -> }
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Threat Filters",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            val filters = listOf(
                "Malware",
                "Phishing",
                "Data Breach",
                "Unauthorized Access",
                "Network Intrusion",
                "Social Engineering"
            )
            
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filters.forEach { filter ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selectedFilters.contains(filter),
                            onCheckedChange = { checked ->
                                onFilterChange(filter, checked)
                            }
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Text(
                            text = filter,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

// Privacy Components
@Composable
fun PrivacyOverviewCard(
    privacyMetrics: Map<String, Any> = mapOf(
        "dataCollected" to "Minimal",
        "thirdPartySharing" to "None",
        "encryptionLevel" to "AES-256",
        "retentionPeriod" to "30 days",
        "userConsent" to "Explicit"
    ),
    onMetricClick: (String) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Privacy Overview",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            privacyMetrics.forEach { (key, value) ->
                PrivacyMetricItem(
                    label = capitalizeWords(key.replace("([A-Z])".toRegex(), " $1").trim()),
                    value = value.toString(),
                    onClick = { onMetricClick(key) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun PrivacyMetricItem(
    label: String,
    value: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium
        )
        
        Surface(
            color = Blue.copy(alpha = 0.1f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = value,
                color = Blue,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}

// Permissions Components
@Composable
fun PermissionsOverviewCard(
    permissions: List<Map<String, Any>> = listOf(
        mapOf("name" to "Camera", "status" to "granted", "usage" to "Photo capture"),
        mapOf("name" to "Location", "status" to "denied", "usage" to "Maps and navigation"),
        mapOf("name" to "Microphone", "status" to "granted", "usage" to "Voice recording"),
        mapOf("name" to "Storage", "status" to "granted", "usage" to "File access")
    ),
    onPermissionClick: (String) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Permissions Overview",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(permissions) { permission ->
                    PermissionItem(
                        permission = permission,
                        onClick = { onPermissionClick(permission["name"] as? String ?: "") }
                    )
                }
            }
        }
    }
}

@Composable
fun PermissionItem(
    permission: Map<String, Any>,
    onClick: () -> Unit
) {
    val name = permission["name"] as? String ?: "Unknown"
    val status = permission["status"] as? String ?: "unknown"
    val usage = permission["usage"] as? String ?: "No description"
    
    val (statusColor, statusIcon) = when (status.lowercase()) {
        "granted" -> Green to Icons.Default.CheckCircle
        "denied" -> Red to Icons.Default.Cancel
        "pending" -> Orange to Icons.Default.Schedule
        else -> Color.Gray to Icons.Default.Help
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = statusIcon,
                contentDescription = null,
                tint = statusColor
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = usage,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Surface(
                color = statusColor.copy(alpha = 0.1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = capitalizeWords(status),
                    color = statusColor,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
fun PermissionGroupCard(
    groups: Map<String, List<String>> = mapOf(
        "Essential" to listOf("Storage", "Network"),
        "Optional" to listOf("Camera", "Microphone", "Location"),
        "Dangerous" to listOf("Contacts", "SMS", "Phone")
    ),
    onGroupClick: (String) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Permission Groups",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            groups.forEach { (groupName, permissions) ->
                PermissionGroupItem(
                    groupName = groupName,
                    permissions = permissions,
                    onClick = { onGroupClick(groupName) }
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun PermissionGroupItem(
    groupName: String,
    permissions: List<String>,
    onClick: () -> Unit
) {
    val groupColor = when (groupName.lowercase()) {
        "essential" -> Green
        "optional" -> Blue
        "dangerous" -> Red
        else -> Color.Gray
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = groupColor.copy(alpha = 0.05f)
        ),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(groupColor.copy(alpha = 0.3f))
        )
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = when (groupName.lowercase()) {
                        "essential" -> Icons.Default.Security
                        "optional" -> Icons.Default.Settings
                        "dangerous" -> Icons.Default.Warning
                        else -> Icons.Default.Group
                    },
                    contentDescription = null,
                    tint = groupColor
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = groupName,
                    fontWeight = FontWeight.Medium,
                    color = groupColor
                )
                
                Spacer(modifier = Modifier.weight(1f))
                
                Surface(
                    color = groupColor.copy(alpha = 0.1f),
                    shape = CircleShape
                ) {
                    Text(
                        text = permissions.size.toString(),
                        color = groupColor,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(6.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Text(
                text = permissions.joinToString(", "),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// Encryption Components
@Composable
fun EncryptionOverviewCard(
    encryptionStatus: Map<String, String> = mapOf(
        "Data at Rest" to "AES-256",
        "Data in Transit" to "TLS 1.3",
        "Database" to "Encrypted",
        "Backups" to "Encrypted",
        "Key Management" to "HSM"
    ),
    onStatusClick: (String) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Encryption Overview",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            encryptionStatus.forEach { (category, status) ->
                EncryptionStatusItem(
                    category = category,
                    status = status,
                    onClick = { onStatusClick(category) }
                )
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun EncryptionStatusItem(
    category: String,
    status: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                tint = Green,
                modifier = Modifier.size(16.dp)
            )
            
            Spacer(modifier = Modifier.width(8.dp))
            
            Text(
                text = category,
                style = MaterialTheme.typography.bodyMedium
            )
        }
        
        Surface(
            color = Green.copy(alpha = 0.1f),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = status,
                color = Green,
                fontSize = 12.sp,
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
        }
    }
}

@Composable
fun EncryptionConfigCard(
    configs: List<Map<String, Any>> = listOf(
        mapOf("name" to "Database Encryption", "algorithm" to "AES-256-GCM", "enabled" to true),
        mapOf("name" to "File Encryption", "algorithm" to "ChaCha20-Poly1305", "enabled" to true),
        mapOf("name" to "Communication Encryption", "algorithm" to "TLS 1.3", "enabled" to true),
        mapOf("name" to "Backup Encryption", "algorithm" to "AES-256-CBC", "enabled" to false)
    ),
    onConfigClick: (String) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Encryption Configuration",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(configs) { config ->
                    EncryptionConfigItem(
                        config = config,
                        onClick = { onConfigClick(config["name"] as? String ?: "") }
                    )
                }
            }
        }
    }
}

@Composable
fun EncryptionConfigItem(
    config: Map<String, Any>,
    onClick: () -> Unit
) {
    val name = config["name"] as? String ?: "Unknown"
    val algorithm = config["algorithm"] as? String ?: "Unknown"
    val enabled = config["enabled"] as? Boolean ?: false
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (enabled) Icons.Default.Lock else Icons.Default.LockOpen,
                contentDescription = null,
                tint = if (enabled) Green else Red
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = name,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = algorithm,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            
            Switch(
                checked = enabled,
                onCheckedChange = { /* Handle toggle */ }
            )
        }
    }
}

// Compliance Components
@Composable
fun ComplianceOverviewCard(
    overallScore: Int = 85,
    standards: List<String> = listOf("GDPR", "HIPAA", "SOC 2", "ISO 27001"),
    onViewDetails: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Compliance Overview",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Overall Score",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = "$overallScore%",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = getComplianceScoreColor(overallScore)
                    )
                }
                
                CircularProgressIndicator(
                    progress = overallScore / 100f,
                    color = getComplianceScoreColor(overallScore),
                    modifier = Modifier.size(60.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Standards: ${standards.joinToString(", ")}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Button(
                onClick = onViewDetails,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Detailed Report")
            }
        }
    }
}

@Composable
fun ComplianceStandardCard(
    standard: Map<String, Any> = mapOf(
        "name" to "GDPR",
        "score" to 92,
        "requirements" to 25,
        "compliant" to 23,
        "lastAudit" to System.currentTimeMillis() - 86400000 * 30
    ),
    onViewRequirements: () -> Unit = {}
) {
    val name = standard["name"] as? String ?: "Unknown"
    val score = standard["score"] as? Int ?: 0
    val requirements = standard["requirements"] as? Int ?: 0
    val compliant = standard["compliant"] as? Int ?: 0
    val lastAudit = standard["lastAudit"] as? Long ?: 0L
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                
                Surface(
                    color = getComplianceScoreColor(score).copy(alpha = 0.1f),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "$score%",
                        color = getComplianceScoreColor(score),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Requirements",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "$compliant / $requirements",
                        fontWeight = FontWeight.Medium
                    )
                }
                
                Column {
                    Text(
                        text = "Last Audit",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = formatRelativeTime(lastAudit),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LinearProgressIndicator(
                progress = compliant.toFloat() / requirements.toFloat(),
                modifier = Modifier.fillMaxWidth(),
                color = getComplianceScoreColor(score)
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            OutlinedButton(
                onClick = onViewRequirements,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Requirements")
            }
        }
    }
}

// Audit Components
@Composable
fun AuditControlsCard(
    onStartAudit: (String) -> Unit = {},
    onViewHistory: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Audit Controls",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            val auditTypes = listOf(
                "Security Audit",
                "Compliance Audit",
                "Privacy Audit",
                "Performance Audit"
            )
            
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                auditTypes.forEach { auditType ->
                    OutlinedButton(
                        onClick = { onStartAudit(auditType) },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Default.Assessment,
                            contentDescription = null
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Start $auditType")
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Button(
                    onClick = onViewHistory,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("View Audit History")
                }
            }
        }
    }
}

@Composable
fun AuditEntryCard(
    entries: List<Map<String, Any>> = listOf(
        mapOf(
            "id" to "audit1",
            "type" to "Security Audit",
            "status" to "completed",
            "timestamp" to System.currentTimeMillis() - 86400000,
            "findings" to 3,
            "severity" to "medium"
        ),
        mapOf(
            "id" to "audit2",
            "type" to "Compliance Audit",
            "status" to "running",
            "timestamp" to System.currentTimeMillis() - 3600000,
            "findings" to 0,
            "severity" to "low"
        )
    ),
    onEntryClick: (String) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Recent Audit Entries",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(entries) { entry ->
                    AuditEntryItem(
                        entry = entry,
                        onClick = { onEntryClick(entry["id"] as? String ?: "") }
                    )
                }
            }
        }
    }
}

@Composable
fun AuditEntryItem(
    entry: Map<String, Any>,
    onClick: () -> Unit
) {
    val type = entry["type"] as? String ?: "Unknown"
    val status = entry["status"] as? String ?: "unknown"
    val timestamp = entry["timestamp"] as? Long ?: 0L
    val findings = entry["findings"] as? Int ?: 0
    val severity = entry["severity"] as? String ?: "unknown"
    
    val statusColor = when (status.lowercase()) {
        "completed" -> Green
        "running" -> Blue
        "failed" -> Red
        "pending" -> Orange
        else -> Color.Gray
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Assessment,
                contentDescription = null,
                tint = statusColor
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = type,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = formatRelativeTime(timestamp),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (findings > 0) {
                    Text(
                        text = "$findings findings ($severity severity)",
                        style = MaterialTheme.typography.bodySmall,
                        color = getAlertColor(severity)
                    )
                }
            }
            
            StatusChip(status = status)
        }
    }
}

// Utility functions
fun getComplianceScoreColor(score: Int): Color {
    return when {
        score >= 90 -> Green
        score >= 70 -> Orange
        score >= 50 -> Color(0xFFFF9800) // Amber
        else -> Red
    }
}