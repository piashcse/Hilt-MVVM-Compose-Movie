package com.xiaomi.base.components.enterprise.data

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.*

/**
 * Data Classification Level
 */
enum class DataClassification {
    PUBLIC,
    INTERNAL,
    CONFIDENTIAL,
    RESTRICTED,
    TOP_SECRET
}

/**
 * Data Type
 */
enum class DataType {
    PERSONAL_DATA,
    FINANCIAL_DATA,
    HEALTH_DATA,
    INTELLECTUAL_PROPERTY,
    BUSINESS_DATA,
    SYSTEM_DATA,
    LOG_DATA,
    METADATA
}

/**
 * Data Lifecycle Stage
 */
enum class DataLifecycleStage {
    CREATION,
    STORAGE,
    PROCESSING,
    SHARING,
    ARCHIVAL,
    DELETION
}

/**
 * Data Asset
 */
data class DataAsset(
    val id: String,
    val name: String,
    val description: String,
    val type: DataType,
    val classification: DataClassification,
    val owner: String,
    val steward: String,
    val location: String,
    val size: Long, // in bytes
    val createdDate: Date,
    val lastModified: Date,
    val retentionPeriod: Int, // in days
    val tags: List<String>,
    val metadata: Map<String, String>,
    val qualityScore: Int, // 0-100
    val complianceStatus: ComplianceStatus
)

/**
 * Data Policy
 */
data class DataPolicy(
    val id: String,
    val name: String,
    val description: String,
    val type: PolicyType,
    val scope: PolicyScope,
    val rules: List<PolicyRule>,
    val effectiveDate: Date,
    val expiryDate: Date?,
    val owner: String,
    val status: PolicyStatus,
    val version: String
)

/**
 * Policy Type
 */
enum class PolicyType {
    DATA_RETENTION,
    DATA_ACCESS,
    DATA_SHARING,
    DATA_QUALITY,
    DATA_SECURITY,
    DATA_PRIVACY,
    DATA_BACKUP,
    DATA_ARCHIVAL
}

/**
 * Policy Scope
 */
enum class PolicyScope {
    GLOBAL,
    REGIONAL,
    DEPARTMENTAL,
    PROJECT_SPECIFIC,
    DATA_TYPE_SPECIFIC
}

/**
 * Policy Rule
 */
data class PolicyRule(
    val id: String,
    val condition: String,
    val action: String,
    val parameters: Map<String, String>
)

/**
 * Policy Status
 */
enum class PolicyStatus {
    DRAFT,
    ACTIVE,
    INACTIVE,
    DEPRECATED,
    UNDER_REVIEW
}

/**
 * Compliance Status
 */
enum class ComplianceStatus {
    COMPLIANT,
    NON_COMPLIANT,
    PARTIALLY_COMPLIANT,
    UNKNOWN,
    UNDER_REVIEW
}

/**
 * Data Lineage
 */
data class DataLineage(
    val assetId: String,
    val sources: List<DataSource>,
    val transformations: List<DataTransformation>,
    val destinations: List<DataDestination>,
    val dependencies: List<String>
)

/**
 * Data Source
 */
data class DataSource(
    val id: String,
    val name: String,
    val type: String,
    val location: String,
    val lastUpdated: Date
)

/**
 * Data Transformation
 */
data class DataTransformation(
    val id: String,
    val name: String,
    val type: String,
    val description: String,
    val timestamp: Date
)

/**
 * Data Destination
 */
data class DataDestination(
    val id: String,
    val name: String,
    val type: String,
    val location: String,
    val purpose: String
)

/**
 * Data Quality Metrics
 */
data class DataQualityMetrics(
    val completeness: Int, // 0-100
    val accuracy: Int, // 0-100
    val consistency: Int, // 0-100
    val timeliness: Int, // 0-100
    val validity: Int, // 0-100
    val uniqueness: Int, // 0-100
    val overallScore: Int // 0-100
)

/**
 * Data Governance Configuration
 */
data class DataGovernanceConfig(
    val enableDataCatalog: Boolean = true,
    val enablePolicyManagement: Boolean = true,
    val enableLineageTracking: Boolean = true,
    val enableQualityMonitoring: Boolean = true,
    val enableComplianceTracking: Boolean = true,
    val autoClassification: Boolean = false,
    val retentionPolicyEnforcement: Boolean = true,
    val accessControlEnabled: Boolean = true
)

/**
 * Data Governance Component
 * Comprehensive data governance and management
 * 
 * @param config Data governance configuration
 * @param onAssetSelected Callback when data asset is selected
 * @param onPolicyAction Callback when policy action is taken
 */
@Composable
fun DataGovernanceComponent(
    config: DataGovernanceConfig = DataGovernanceConfig(),
    onAssetSelected: (DataAsset) -> Unit = {},
    onPolicyAction: (DataPolicy, String) -> Unit = { _, _ -> }
) {
    var selectedTab by remember { mutableStateOf(0) }
    var dataAssets by remember { mutableStateOf<List<DataAsset>>(emptyList()) }
    var dataPolicies by remember { mutableStateOf<List<DataPolicy>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    val context = LocalContext.current
    
    LaunchedEffect(config) {
        isLoading = true
        delay(1000) // Simulate loading
        dataAssets = generateMockDataAssets()
        dataPolicies = generateMockDataPolicies()
        isLoading = false
    }
    
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Tab navigation
        DataGovernanceTabRow(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Tab content
        if (isLoading) {
            LoadingIndicator()
        } else {
            when (selectedTab) {
                0 -> DataCatalogTab(
                    assets = dataAssets,
                    onAssetSelected = onAssetSelected
                )
                1 -> PolicyManagementTab(
                    policies = dataPolicies,
                    onPolicyAction = onPolicyAction
                )
                2 -> DataLineageTab(
                    assets = dataAssets
                )
                3 -> QualityMonitoringTab(
                    assets = dataAssets
                )
                4 -> ComplianceTrackingTab(
                    assets = dataAssets,
                    policies = dataPolicies
                )
            }
        }
    }
}

/**
 * Data Catalog Component
 * Browse and manage data assets
 * 
 * @param assets List of data assets
 * @param onAssetClick Callback when asset is clicked
 * @param onAssetAction Callback when action is taken on asset
 */
@Composable
fun DataCatalogComponent(
    assets: List<DataAsset>,
    onAssetClick: (DataAsset) -> Unit = {},
    onAssetAction: (DataAsset, String) -> Unit = { _, _ -> }
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedClassification by remember { mutableStateOf<DataClassification?>(null) }
    var selectedType by remember { mutableStateOf<DataType?>(null) }
    
    val filteredAssets = assets.filter { asset ->
        (searchQuery.isEmpty() || asset.name.contains(searchQuery, ignoreCase = true)) &&
        (selectedClassification == null || asset.classification == selectedClassification) &&
        (selectedType == null || asset.type == selectedType)
    }
    
    Column {
        // Search and filters
        DataCatalogFilters(
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            selectedClassification = selectedClassification,
            onClassificationSelected = { selectedClassification = it },
            selectedType = selectedType,
            onTypeSelected = { selectedType = it }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Assets list
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredAssets) { asset ->
                DataAssetCard(
                    asset = asset,
                    onClick = { onAssetClick(asset) },
                    onAction = { action -> onAssetAction(asset, action) }
                )
            }
        }
    }
}

/**
 * Data Policy Management Component
 * Manage data governance policies
 * 
 * @param policies List of data policies
 * @param onPolicyClick Callback when policy is clicked
 * @param onPolicyAction Callback when action is taken on policy
 */
@Composable
fun DataPolicyManagementComponent(
    policies: List<DataPolicy>,
    onPolicyClick: (DataPolicy) -> Unit = {},
    onPolicyAction: (DataPolicy, String) -> Unit = { _, _ -> }
) {
    var selectedPolicyType by remember { mutableStateOf<PolicyType?>(null) }
    var selectedStatus by remember { mutableStateOf<PolicyStatus?>(null) }
    
    val filteredPolicies = policies.filter { policy ->
        (selectedPolicyType == null || policy.type == selectedPolicyType) &&
        (selectedStatus == null || policy.status == selectedStatus)
    }
    
    Column {
        // Policy filters
        DataPolicyFilters(
            selectedType = selectedPolicyType,
            onTypeSelected = { selectedPolicyType = it },
            selectedStatus = selectedStatus,
            onStatusSelected = { selectedStatus = it }
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Policies list
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(filteredPolicies) { policy ->
                DataPolicyCard(
                    policy = policy,
                    onClick = { onPolicyClick(policy) },
                    onAction = { action -> onPolicyAction(policy, action) }
                )
            }
        }
    }
}

/**
 * Data Lineage Visualization Component
 * Visualize data flow and dependencies
 * 
 * @param assetId ID of the asset to show lineage for
 * @param lineage Data lineage information
 */
@Composable
fun DataLineageVisualizationComponent(
    assetId: String,
    lineage: DataLineage?
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Data Lineage",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (lineage != null) {
                // Sources
                if (lineage.sources.isNotEmpty()) {
                    Text(
                        text = "Sources (${lineage.sources.size})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    lineage.sources.forEach { source ->
                        LineageNodeCard(
                            name = source.name,
                            type = source.type,
                            location = source.location,
                            icon = Icons.Default.Input
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                }
                
                // Transformations
                if (lineage.transformations.isNotEmpty()) {
                    Text(
                        text = "Transformations (${lineage.transformations.size})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    lineage.transformations.forEach { transformation ->
                        LineageNodeCard(
                            name = transformation.name,
                            type = transformation.type,
                            location = transformation.description,
                            icon = Icons.Default.Transform
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                }
                
                // Destinations
                if (lineage.destinations.isNotEmpty()) {
                    Text(
                        text = "Destinations (${lineage.destinations.size})",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    lineage.destinations.forEach { destination ->
                        LineageNodeCard(
                            name = destination.name,
                            type = destination.type,
                            location = destination.location,
                            icon = Icons.Default.Output
                        )
                    }
                }
            } else {
                Text(
                    text = "No lineage information available",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

/**
 * Tab Row
 */
@Composable
private fun DataGovernanceTabRow(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val tabs = listOf(
        "Data Catalog",
        "Policies",
        "Lineage",
        "Quality",
        "Compliance"
    )
    
    TabRow(
        selectedTabIndex = selectedTab
    ) {
        tabs.forEachIndexed { index, title ->
            Tab(
                selected = selectedTab == index,
                onClick = { onTabSelected(index) },
                text = { Text(title) }
            )
        }
    }
}

/**
 * Data Catalog Tab
 */
@Composable
private fun DataCatalogTab(
    assets: List<DataAsset>,
    onAssetSelected: (DataAsset) -> Unit
) {
    DataCatalogComponent(
        assets = assets,
        onAssetClick = onAssetSelected
    )
}

/**
 * Policy Management Tab
 */
@Composable
private fun PolicyManagementTab(
    policies: List<DataPolicy>,
    onPolicyAction: (DataPolicy, String) -> Unit
) {
    DataPolicyManagementComponent(
        policies = policies,
        onPolicyAction = onPolicyAction
    )
}

/**
 * Data Lineage Tab
 */
@Composable
private fun DataLineageTab(
    assets: List<DataAsset>
) {
    var selectedAsset by remember { mutableStateOf<DataAsset?>(null) }
    
    Column {
        // Asset selector
        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Select Asset for Lineage",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                LazyColumn(
                    modifier = Modifier.height(200.dp)
                ) {
                    items(assets.take(10)) { asset ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 2.dp),
                            onClick = { selectedAsset = asset }
                        ) {
                            Text(
                                text = asset.name,
                                modifier = Modifier.padding(12.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Lineage visualization
        selectedAsset?.let { asset ->
            DataLineageVisualizationComponent(
                assetId = asset.id,
                lineage = generateMockLineage(asset.id)
            )
        }
    }
}

/**
 * Quality Monitoring Tab
 */
@Composable
private fun QualityMonitoringTab(
    assets: List<DataAsset>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            // Quality overview
            DataQualityOverviewCard(
                assets = assets
            )
        }
        
        items(assets.take(10)) { asset ->
            DataQualityCard(
                asset = asset,
                metrics = generateMockQualityMetrics()
            )
        }
    }
}

/**
 * Compliance Tracking Tab
 */
@Composable
private fun ComplianceTrackingTab(
    assets: List<DataAsset>,
    policies: List<DataPolicy>
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            // Compliance overview
            ComplianceOverviewCard(
                assets = assets,
                policies = policies
            )
        }
        
        items(assets.filter { it.complianceStatus != ComplianceStatus.COMPLIANT }) { asset ->
            ComplianceIssueCard(
                asset = asset
            )
        }
    }
}

/**
 * Data Catalog Filters
 */
@Composable
private fun DataCatalogFilters(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    selectedClassification: DataClassification?,
    onClassificationSelected: (DataClassification?) -> Unit,
    selectedType: DataType?,
    onTypeSelected: (DataType?) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Search
            OutlinedTextField(
                value = searchQuery,
                onValueChange = onSearchQueryChange,
                label = { Text("Search assets") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search"
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Classification filter
            Text(
                text = "Classification:",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedClassification == null,
                    onClick = { onClassificationSelected(null) },
                    label = { Text("All") }
                )
                
                DataClassification.values().take(4).forEach { classification ->
                    FilterChip(
                        selected = selectedClassification == classification,
                        onClick = { onClassificationSelected(classification) },
                        label = { Text(classification.name) }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Type filter
            Text(
                text = "Type:",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedType == null,
                    onClick = { onTypeSelected(null) },
                    label = { Text("All") }
                )
                
                DataType.values().take(4).forEach { type ->
                    FilterChip(
                        selected = selectedType == type,
                        onClick = { onTypeSelected(type) },
                        label = { Text(type.name.replace("_", " ")) }
                    )
                }
            }
        }
    }
}

/**
 * Data Asset Card
 */
@Composable
private fun DataAssetCard(
    asset: DataAsset,
    onClick: () -> Unit,
    onAction: (String) -> Unit
) {
    val classificationColor = when (asset.classification) {
        DataClassification.PUBLIC -> Color(0xFF4CAF50)
        DataClassification.INTERNAL -> Color(0xFF2196F3)
        DataClassification.CONFIDENTIAL -> Color(0xFFFF9800)
        DataClassification.RESTRICTED -> Color(0xFFFF5722)
        DataClassification.TOP_SECRET -> Color(0xFFD32F2F)
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = asset.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Text(
                        text = asset.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Badge(
                    containerColor = classificationColor
                ) {
                    Text(
                        text = asset.classification.name,
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Owner: ${asset.owner}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Text(
                        text = "Type: ${asset.type.name.replace("_", " ")}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "Quality: ${asset.qualityScore}/100",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Text(
                        text = formatFileSize(asset.size),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { onAction("view") },
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(
                        text = "View",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                
                OutlinedButton(
                    onClick = { onAction("lineage") },
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(
                        text = "Lineage",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                
                OutlinedButton(
                    onClick = { onAction("quality") },
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(
                        text = "Quality",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

/**
 * Data Policy Filters
 */
@Composable
private fun DataPolicyFilters(
    selectedType: PolicyType?,
    onTypeSelected: (PolicyType?) -> Unit,
    selectedStatus: PolicyStatus?,
    onStatusSelected: (PolicyStatus?) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Type filter
            Text(
                text = "Policy Type:",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedType == null,
                    onClick = { onTypeSelected(null) },
                    label = { Text("All") }
                )
                
                PolicyType.values().take(4).forEach { type ->
                    FilterChip(
                        selected = selectedType == type,
                        onClick = { onTypeSelected(type) },
                        label = { Text(type.name.replace("_", " ")) }
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Status filter
            Text(
                text = "Status:",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedStatus == null,
                    onClick = { onStatusSelected(null) },
                    label = { Text("All") }
                )
                
                PolicyStatus.values().forEach { status ->
                    FilterChip(
                        selected = selectedStatus == status,
                        onClick = { onStatusSelected(status) },
                        label = { Text(status.name.replace("_", " ")) }
                    )
                }
            }
        }
    }
}

/**
 * Data Policy Card
 */
@Composable
private fun DataPolicyCard(
    policy: DataPolicy,
    onClick: () -> Unit,
    onAction: (String) -> Unit
) {
    val statusColor = when (policy.status) {
        PolicyStatus.ACTIVE -> Color(0xFF4CAF50)
        PolicyStatus.DRAFT -> Color(0xFFFF9800)
        PolicyStatus.INACTIVE -> Color(0xFF9E9E9E)
        PolicyStatus.DEPRECATED -> Color(0xFFFF5722)
        PolicyStatus.UNDER_REVIEW -> Color(0xFF2196F3)
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = policy.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Text(
                        text = policy.description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Badge(
                    containerColor = statusColor
                ) {
                    Text(
                        text = policy.status.name,
                        color = Color.White,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "Type: ${policy.type.name.replace("_", " ")}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Text(
                        text = "Owner: ${policy.owner}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "Version: ${policy.version}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Text(
                        text = "Rules: ${policy.rules.size}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = { onAction("view") },
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(
                        text = "View",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
                
                if (policy.status == PolicyStatus.DRAFT) {
                    Button(
                        onClick = { onAction("activate") },
                        modifier = Modifier.height(32.dp)
                    ) {
                        Text(
                            text = "Activate",
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
                
                OutlinedButton(
                    onClick = { onAction("edit") },
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(
                        text = "Edit",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

/**
 * Lineage Node Card
 */
@Composable
private fun LineageNodeCard(
    name: String,
    type: String,
    location: String,
    icon: ImageVector
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
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
                imageVector = icon,
                contentDescription = type,
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                
                Text(
                    text = "$type • $location",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Data Quality Overview Card
 */
@Composable
private fun DataQualityOverviewCard(
    assets: List<DataAsset>
) {
    val averageQuality = if (assets.isNotEmpty()) {
        assets.map { it.qualityScore }.average().toInt()
    } else {
        0
    }
    
    val qualityDistribution = assets.groupBy {
        when (it.qualityScore) {
            in 90..100 -> "Excellent"
            in 70..89 -> "Good"
            in 50..69 -> "Fair"
            else -> "Poor"
        }
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Data Quality Overview",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                QualityMetric(
                    title = "Average Score",
                    value = "$averageQuality/100",
                    icon = Icons.Default.Assessment
                )
                
                QualityMetric(
                    title = "Excellent",
                    value = (qualityDistribution["Excellent"]?.size ?: 0).toString(),
                    icon = Icons.Default.Star
                )
                
                QualityMetric(
                    title = "Good",
                    value = (qualityDistribution["Good"]?.size ?: 0).toString(),
                    icon = Icons.Default.ThumbUp
                )
                
                QualityMetric(
                    title = "Needs Work",
                    value = ((qualityDistribution["Fair"]?.size ?: 0) + (qualityDistribution["Poor"]?.size ?: 0)).toString(),
                    icon = Icons.Default.Warning
                )
            }
        }
    }
}

/**
 * Quality Metric
 */
@Composable
private fun QualityMetric(
    title: String,
    value: String,
    icon: ImageVector
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }
}

/**
 * Data Quality Card
 */
@Composable
private fun DataQualityCard(
    asset: DataAsset,
    metrics: DataQualityMetrics
) {
    Card(
        modifier = Modifier.fillMaxWidth()
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
                    text = asset.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Text(
                    text = "${metrics.overallScore}/100",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = when {
                        metrics.overallScore >= 90 -> Color(0xFF4CAF50)
                        metrics.overallScore >= 70 -> Color(0xFFFF9800)
                        else -> MaterialTheme.colorScheme.error
                    }
                )
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Quality dimensions
            QualityDimension("Completeness", metrics.completeness)
            QualityDimension("Accuracy", metrics.accuracy)
            QualityDimension("Consistency", metrics.consistency)
            QualityDimension("Timeliness", metrics.timeliness)
            QualityDimension("Validity", metrics.validity)
            QualityDimension("Uniqueness", metrics.uniqueness)
        }
    }
}

/**
 * Quality Dimension
 */
@Composable
private fun QualityDimension(
    name: String,
    score: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.width(80.dp)
        )
        
        LinearProgressIndicator(
            progress = score / 100f,
            modifier = Modifier
                .weight(1f)
                .height(4.dp)
                .clip(RoundedCornerShape(2.dp)),
            color = when {
                score >= 90 -> Color(0xFF4CAF50)
                score >= 70 -> Color(0xFFFF9800)
                else -> MaterialTheme.colorScheme.error
            }
        )
        
        Text(
            text = "$score%",
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.width(40.dp),
            textAlign = TextAlign.End
        )
    }
}

/**
 * Compliance Overview Card
 */
@Composable
private fun ComplianceOverviewCard(
    assets: List<DataAsset>,
    policies: List<DataPolicy>
) {
    val compliantAssets = assets.count { it.complianceStatus == ComplianceStatus.COMPLIANT }
    val activePolicies = policies.count { it.status == PolicyStatus.ACTIVE }
    val nonCompliantAssets = assets.count { it.complianceStatus == ComplianceStatus.NON_COMPLIANT }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Compliance Overview",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ComplianceMetric(
                    title = "Compliant Assets",
                    value = "$compliantAssets/${assets.size}",
                    icon = Icons.Default.CheckCircle
                )
                
                ComplianceMetric(
                    title = "Active Policies",
                    value = activePolicies.toString(),
                    icon = Icons.Default.Policy
                )
                
                ComplianceMetric(
                    title = "Issues",
                    value = nonCompliantAssets.toString(),
                    icon = Icons.Default.Warning
                )
            }
        }
    }
}

/**
 * Compliance Metric
 */
@Composable
private fun ComplianceMetric(
    title: String,
    value: String,
    icon: ImageVector
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.onPrimaryContainer
        )
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            textAlign = TextAlign.Center
        )
    }
}

/**
 * Compliance Issue Card
 */
@Composable
private fun ComplianceIssueCard(
    asset: DataAsset
) {
    val statusColor = when (asset.complianceStatus) {
        ComplianceStatus.NON_COMPLIANT -> MaterialTheme.colorScheme.error
        ComplianceStatus.PARTIALLY_COMPLIANT -> Color(0xFFFF9800)
        ComplianceStatus.UNDER_REVIEW -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.onSurfaceVariant
    }
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Warning,
                contentDescription = "Compliance Issue",
                modifier = Modifier.size(24.dp),
                tint = statusColor
            )
            
            Spacer(modifier = Modifier.width(12.dp))
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = asset.name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium
                )
                
                Text(
                    text = "Status: ${asset.complianceStatus.name.replace("_", " ")}",
                    style = MaterialTheme.typography.bodySmall,
                    color = statusColor
                )
            }
            
            OutlinedButton(
                onClick = { /* Handle remediation */ },
                modifier = Modifier.height(32.dp)
            ) {
                Text(
                    text = "Remediate",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

/**
 * Loading Indicator
 */
@Composable
private fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

/**
 * Helper Functions
 */

/**
 * Generate mock data assets
 */
private fun generateMockDataAssets(): List<DataAsset> {
    return listOf(
        DataAsset(
            id = "asset-1",
            name = "Customer Database",
            description = "Primary customer information database",
            type = DataType.PERSONAL_DATA,
            classification = DataClassification.CONFIDENTIAL,
            owner = "John Smith",
            steward = "Jane Doe",
            location = "/data/customers",
            size = 1024L * 1024 * 1024 * 5, // 5GB
            createdDate = Date(),
            lastModified = Date(),
            retentionPeriod = 2555, // 7 years
            tags = listOf("customer", "personal", "gdpr"),
            metadata = mapOf("schema" to "v2.1", "format" to "postgresql"),
            qualityScore = 85,
            complianceStatus = ComplianceStatus.COMPLIANT
        ),
        DataAsset(
            id = "asset-2",
            name = "Financial Transactions",
            description = "Transaction history and financial data",
            type = DataType.FINANCIAL_DATA,
            classification = DataClassification.RESTRICTED,
            owner = "Finance Team",
            steward = "Bob Wilson",
            location = "/data/finance",
            size = 1024L * 1024 * 1024 * 10, // 10GB
            createdDate = Date(),
            lastModified = Date(),
            retentionPeriod = 3650, // 10 years
            tags = listOf("finance", "transactions", "pci"),
            metadata = mapOf("schema" to "v1.5", "format" to "mysql"),
            qualityScore = 92,
            complianceStatus = ComplianceStatus.PARTIALLY_COMPLIANT
        )
    )
}

/**
 * Generate mock data policies
 */
private fun generateMockDataPolicies(): List<DataPolicy> {
    return listOf(
        DataPolicy(
            id = "policy-1",
            name = "Data Retention Policy",
            description = "Defines how long different types of data should be retained",
            type = PolicyType.DATA_RETENTION,
            scope = PolicyScope.GLOBAL,
            rules = listOf(
                PolicyRule(
                    id = "rule-1",
                    condition = "data_type == 'personal'",
                    action = "retain_for_years",
                    parameters = mapOf("years" to "7")
                )
            ),
            effectiveDate = Date(),
            expiryDate = null,
            owner = "Data Protection Officer",
            status = PolicyStatus.ACTIVE,
            version = "1.0"
        ),
        DataPolicy(
            id = "policy-2",
            name = "Data Access Control Policy",
            description = "Controls who can access what data",
            type = PolicyType.DATA_ACCESS,
            scope = PolicyScope.GLOBAL,
            rules = listOf(
                PolicyRule(
                    id = "rule-2",
                    condition = "classification == 'confidential'",
                    action = "require_approval",
                    parameters = mapOf("approver" to "data_owner")
                )
            ),
            effectiveDate = Date(),
            expiryDate = null,
            owner = "Security Team",
            status = PolicyStatus.ACTIVE,
            version = "2.1"
        )
    )
}

/**
 * Generate mock lineage
 */
private fun generateMockLineage(assetId: String): DataLineage {
    return DataLineage(
        assetId = assetId,
        sources = listOf(
            DataSource(
                id = "source-1",
                name = "CRM System",
                type = "Database",
                location = "crm.company.com",
                lastUpdated = Date()
            ),
            DataSource(
                id = "source-2",
                name = "Web Analytics",
                type = "API",
                location = "analytics.company.com",
                lastUpdated = Date()
            )
        ),
        transformations = listOf(
            DataTransformation(
                id = "transform-1",
                name = "Data Cleansing",
                type = "ETL",
                description = "Remove duplicates and standardize formats",
                timestamp = Date()
            ),
            DataTransformation(
                id = "transform-2",
                name = "Data Enrichment",
                type = "ETL",
                description = "Add derived fields and calculations",
                timestamp = Date()
            )
        ),
        destinations = listOf(
            DataDestination(
                id = "dest-1",
                name = "Data Warehouse",
                type = "Database",
                location = "warehouse.company.com",
                purpose = "Analytics and Reporting"
            ),
            DataDestination(
                id = "dest-2",
                name = "ML Pipeline",
                type = "Service",
                location = "ml.company.com",
                purpose = "Machine Learning Training"
            )
        ),
        dependencies = listOf("asset-2", "asset-3")
    )
}

/**
 * Generate mock quality metrics
 */
private fun generateMockQualityMetrics(): DataQualityMetrics {
    return DataQualityMetrics(
        completeness = (80..95).random(),
        accuracy = (85..98).random(),
        consistency = (75..90).random(),
        timeliness = (70..95).random(),
        validity = (80..95).random(),
        uniqueness = (90..99).random(),
        overallScore = (75..92).random()
    )
}

/**
 * Format file size
 */
private fun formatFileSize(bytes: Long): String {
    val units = arrayOf("B", "KB", "MB", "GB", "TB")
    var size = bytes.toDouble()
    var unitIndex = 0
    
    while (size >= 1024 && unitIndex < units.size - 1) {
        size /= 1024
        unitIndex++
    }
    
    return "%.1f %s".format(size, units[unitIndex])
}