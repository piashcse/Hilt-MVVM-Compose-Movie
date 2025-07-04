package com.xiaomi.base.components.ai.recommendation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Recommendation Type
 */
enum class RecommendationType {
    CONTENT,
    PRODUCT,
    ACTION,
    PERSON,
    LOCATION,
    CATEGORY,
    TRENDING,
    PERSONALIZED
}

/**
 * Recommendation Priority
 */
enum class RecommendationPriority {
    LOW,
    MEDIUM,
    HIGH,
    URGENT
}

/**
 * Recommendation Source
 */
enum class RecommendationSource {
    USER_BEHAVIOR,
    COLLABORATIVE_FILTERING,
    CONTENT_BASED,
    TRENDING,
    MANUAL,
    HYBRID
}

/**
 * Recommendation Item
 * Represents a single recommendation
 */
data class RecommendationItem(
    val id: String,
    val title: String,
    val description: String? = null,
    val imageUrl: String? = null,
    val type: RecommendationType,
    val priority: RecommendationPriority = RecommendationPriority.MEDIUM,
    val source: RecommendationSource,
    val confidence: Float, // 0.0 to 1.0
    val metadata: Map<String, Any> = emptyMap(),
    val tags: List<String> = emptyList(),
    val actionText: String? = null,
    val deepLink: String? = null
)

/**
 * Recommendation Configuration
 */
data class RecommendationConfig(
    val maxItems: Int = 10,
    val minConfidence: Float = 0.5f,
    val enablePersonalization: Boolean = true,
    val enableTrending: Boolean = true,
    val refreshInterval: Long = 300000L, // 5 minutes
    val displayStyle: RecommendationDisplayStyle = RecommendationDisplayStyle.CARD,
    val groupByType: Boolean = false,
    val showConfidence: Boolean = false,
    val showSource: Boolean = false
)

/**
 * Display Style
 */
enum class RecommendationDisplayStyle {
    CARD,
    LIST,
    GRID,
    CAROUSEL,
    COMPACT
}

/**
 * Smart Recommendation Component
 * AI-powered recommendation system
 * 
 * @param userId User ID for personalization
 * @param context Current context/category
 * @param config Recommendation configuration
 * @param onItemClick Callback when item is clicked
 * @param onItemAction Callback when item action is triggered
 * @param onRefresh Callback to refresh recommendations
 */
@Composable
fun SmartRecommendationComponent(
    userId: String? = null,
    context: String? = null,
    config: RecommendationConfig = RecommendationConfig(),
    onItemClick: (RecommendationItem) -> Unit = {},
    onItemAction: (RecommendationItem, String) -> Unit = { _, _ -> },
    onRefresh: () -> Unit = {},
    content: @Composable (List<RecommendationItem>, Boolean) -> Unit = { items, isLoading ->
        DefaultRecommendationContent(
            items = items,
            isLoading = isLoading,
            config = config,
            onItemClick = onItemClick,
            onItemAction = onItemAction
        )
    }
) {
    var recommendations by remember { mutableStateOf<List<RecommendationItem>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    
    // Simulate recommendation loading
    LaunchedEffect(userId, context) {
        isLoading = true
        try {
            delay(1000) // Simulate API call
            recommendations = generateMockRecommendations(config)
            error = null
        } catch (e: Exception) {
            error = e.message
        } finally {
            isLoading = false
        }
    }
    
    // Auto refresh
    LaunchedEffect(config.refreshInterval) {
        while (true) {
            delay(config.refreshInterval)
            if (!isLoading) {
                try {
                    recommendations = generateMockRecommendations(config)
                } catch (e: Exception) {
                    // Handle error silently for auto refresh
                }
            }
        }
    }
    
    if (error != null) {
        ErrorContent(
            error = error!!,
            onRetry = {
                error = null
                onRefresh()
            }
        )
    } else {
        content(recommendations, isLoading)
    }
}

/**
 * Personalized Recommendation Component
 * Recommendations based on user behavior and preferences
 * 
 * @param userPreferences User preferences
 * @param behaviorHistory User behavior history
 * @param config Configuration
 * @param onItemClick Callback when item is clicked
 */
@Composable
fun PersonalizedRecommendationComponent(
    userPreferences: Map<String, Any> = emptyMap(),
    behaviorHistory: List<String> = emptyList(),
    config: RecommendationConfig = RecommendationConfig(),
    onItemClick: (RecommendationItem) -> Unit = {}
) {
    SmartRecommendationComponent(
        userId = "user_${userPreferences.hashCode()}",
        context = "personalized",
        config = config.copy(enablePersonalization = true),
        onItemClick = onItemClick
    ) { items, isLoading ->
        Column {
            Text(
                text = "Recommended for You",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            
            DefaultRecommendationContent(
                items = items,
                isLoading = isLoading,
                config = config,
                onItemClick = onItemClick
            )
        }
    }
}

/**
 * Trending Recommendation Component
 * Shows trending/popular items
 * 
 * @param category Category to filter trending items
 * @param timeframe Timeframe for trending (e.g., "24h", "7d", "30d")
 * @param config Configuration
 * @param onItemClick Callback when item is clicked
 */
@Composable
fun TrendingRecommendationComponent(
    category: String? = null,
    timeframe: String = "24h",
    config: RecommendationConfig = RecommendationConfig(),
    onItemClick: (RecommendationItem) -> Unit = {}
) {
    SmartRecommendationComponent(
        context = "trending_${category}_$timeframe",
        config = config.copy(enableTrending = true),
        onItemClick = onItemClick
    ) { items, isLoading ->
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Trending Now",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                
                Icon(
                    imageVector = Icons.Default.TrendingUp,
                    contentDescription = "Trending",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            
            DefaultRecommendationContent(
                items = items,
                isLoading = isLoading,
                config = config,
                onItemClick = onItemClick
            )
        }
    }
}

/**
 * Similar Items Recommendation Component
 * Shows items similar to a given item
 * 
 * @param baseItemId ID of the base item
 * @param baseItemType Type of the base item
 * @param config Configuration
 * @param onItemClick Callback when item is clicked
 */
@Composable
fun SimilarItemsRecommendationComponent(
    baseItemId: String,
    baseItemType: RecommendationType,
    config: RecommendationConfig = RecommendationConfig(),
    onItemClick: (RecommendationItem) -> Unit = {}
) {
    SmartRecommendationComponent(
        context = "similar_${baseItemType}_$baseItemId",
        config = config,
        onItemClick = onItemClick
    ) { items, isLoading ->
        Column {
            Text(
                text = "Similar Items",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items) { item ->
                    RecommendationCard(
                        item = item,
                        config = config,
                        onClick = { onItemClick(item) },
                        modifier = Modifier.width(200.dp)
                    )
                }
            }
        }
    }
}

/**
 * Category-based Recommendation Component
 * Shows recommendations for a specific category
 * 
 * @param category Category to get recommendations for
 * @param config Configuration
 * @param onItemClick Callback when item is clicked
 */
@Composable
fun CategoryRecommendationComponent(
    category: String,
    config: RecommendationConfig = RecommendationConfig(),
    onItemClick: (RecommendationItem) -> Unit = {}
) {
    SmartRecommendationComponent(
        context = "category_$category",
        config = config,
        onItemClick = onItemClick
    ) { items, isLoading ->
        Column {
            Text(
                text = "Recommended in $category",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(16.dp)
            )
            
            DefaultRecommendationContent(
                items = items,
                isLoading = isLoading,
                config = config,
                onItemClick = onItemClick
            )
        }
    }
}

/**
 * Default Recommendation Content
 * Default UI for displaying recommendations
 */
@Composable
private fun DefaultRecommendationContent(
    items: List<RecommendationItem>,
    isLoading: Boolean,
    config: RecommendationConfig,
    onItemClick: (RecommendationItem) -> Unit,
    onItemAction: (RecommendationItem, String) -> Unit = { _, _ -> }
) {
    when {
        isLoading -> {
            LoadingContent(config.displayStyle)
        }
        items.isEmpty() -> {
            EmptyContent()
        }
        else -> {
            when (config.displayStyle) {
                RecommendationDisplayStyle.CARD -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(items) { item ->
                            RecommendationCard(
                                item = item,
                                config = config,
                                onClick = { onItemClick(item) },
                                onAction = { action -> onItemAction(item, action) }
                            )
                        }
                    }
                }
                RecommendationDisplayStyle.LIST -> {
                    LazyColumn {
                        items(items) { item ->
                            RecommendationListItem(
                                item = item,
                                config = config,
                                onClick = { onItemClick(item) }
                            )
                        }
                    }
                }
                RecommendationDisplayStyle.CAROUSEL -> {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(items) { item ->
                            RecommendationCard(
                                item = item,
                                config = config,
                                onClick = { onItemClick(item) },
                                modifier = Modifier.width(250.dp)
                            )
                        }
                    }
                }
                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(items) { item ->
                            RecommendationCompactItem(
                                item = item,
                                config = config,
                                onClick = { onItemClick(item) }
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Recommendation Card
 */
@Composable
private fun RecommendationCard(
    item: RecommendationItem,
    config: RecommendationConfig,
    onClick: () -> Unit,
    onAction: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = item.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    item.description?.let { description ->
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                
                // Priority indicator
                if (item.priority == RecommendationPriority.HIGH || item.priority == RecommendationPriority.URGENT) {
                    Icon(
                        imageVector = Icons.Default.PriorityHigh,
                        contentDescription = "High Priority",
                        tint = if (item.priority == RecommendationPriority.URGENT) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.primary
                        },
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            
            // Tags
            if (item.tags.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    items(item.tags.take(3)) { tag ->
                        AssistChip(
                            onClick = { },
                            label = {
                                Text(
                                    text = tag,
                                    style = MaterialTheme.typography.labelSmall
                                )
                            },
                            modifier = Modifier.height(24.dp)
                        )
                    }
                }
            }
            
            // Confidence and source info
            if (config.showConfidence || config.showSource) {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (config.showConfidence) {
                        Text(
                            text = "${(item.confidence * 100).toInt()}% match",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    
                    if (config.showSource) {
                        Text(
                            text = item.source.name.lowercase().replace('_', ' '),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            
            // Action button
            item.actionText?.let { actionText ->
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = { onAction(actionText) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(actionText)
                }
            }
        }
    }
}

/**
 * Recommendation List Item
 */
@Composable
private fun RecommendationListItem(
    item: RecommendationItem,
    config: RecommendationConfig,
    onClick: () -> Unit
) {
    ListItem(
        headlineContent = {
            Text(
                text = item.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        supportingContent = item.description?.let { description ->
            {
                Text(
                    text = description,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        },
        trailingContent = {
            if (config.showConfidence) {
                Text(
                    text = "${(item.confidence * 100).toInt()}%",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        modifier = Modifier.clickable { onClick() }
    )
}

/**
 * Recommendation Compact Item
 */
@Composable
private fun RecommendationCompactItem(
    item: RecommendationItem,
    config: RecommendationConfig,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                
                item.description?.let { description ->
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            
            if (config.showConfidence) {
                Text(
                    text = "${(item.confidence * 100).toInt()}%",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

/**
 * Loading Content
 */
@Composable
private fun LoadingContent(displayStyle: RecommendationDisplayStyle) {
    when (displayStyle) {
        RecommendationDisplayStyle.CAROUSEL -> {
            LazyRow(
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(3) {
                    Card(
                        modifier = Modifier
                            .width(250.dp)
                            .height(150.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
        else -> {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}

/**
 * Empty Content
 */
@Composable
private fun EmptyContent() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.SearchOff,
                contentDescription = "No recommendations",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "No recommendations available",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

/**
 * Error Content
 */
@Composable
private fun ErrorContent(
    error: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Error,
                contentDescription = "Error",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = error,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}

/**
 * Mock data generator for testing
 */
private fun generateMockRecommendations(config: RecommendationConfig): List<RecommendationItem> {
    val mockItems = listOf(
        RecommendationItem(
            id = "1",
            title = "Trending Article: AI in Mobile Development",
            description = "Discover how AI is revolutionizing mobile app development with practical examples and case studies.",
            type = RecommendationType.CONTENT,
            priority = RecommendationPriority.HIGH,
            source = RecommendationSource.TRENDING,
            confidence = 0.95f,
            tags = listOf("AI", "Mobile", "Development"),
            actionText = "Read Now"
        ),
        RecommendationItem(
            id = "2",
            title = "Recommended Product: Smart Watch Pro",
            description = "Based on your recent searches, this smartwatch matches your preferences perfectly.",
            type = RecommendationType.PRODUCT,
            priority = RecommendationPriority.MEDIUM,
            source = RecommendationSource.COLLABORATIVE_FILTERING,
            confidence = 0.87f,
            tags = listOf("Wearable", "Fitness", "Smart"),
            actionText = "View Details"
        ),
        RecommendationItem(
            id = "3",
            title = "Connect with John Doe",
            description = "You have 5 mutual connections and similar interests in technology.",
            type = RecommendationType.PERSON,
            priority = RecommendationPriority.LOW,
            source = RecommendationSource.USER_BEHAVIOR,
            confidence = 0.72f,
            tags = listOf("Network", "Technology", "Professional"),
            actionText = "Connect"
        ),
        RecommendationItem(
            id = "4",
            title = "Popular Location: Tech Hub Cafe",
            description = "Highly rated by developers in your area. Great for remote work and networking.",
            type = RecommendationType.LOCATION,
            priority = RecommendationPriority.MEDIUM,
            source = RecommendationSource.TRENDING,
            confidence = 0.81f,
            tags = listOf("Cafe", "Work", "Networking"),
            actionText = "Get Directions"
        ),
        RecommendationItem(
            id = "5",
            title = "Suggested Action: Update Your Profile",
            description = "Complete your profile to get better recommendations and increase visibility.",
            type = RecommendationType.ACTION,
            priority = RecommendationPriority.HIGH,
            source = RecommendationSource.MANUAL,
            confidence = 1.0f,
            tags = listOf("Profile", "Optimization"),
            actionText = "Update Now"
        )
    )
    
    return mockItems
        .filter { it.confidence >= config.minConfidence }
        .take(config.maxItems)
}

/**
 * Recommendation Engine
 * Core engine for generating recommendations
 */
class RecommendationEngine {
    companion object {
        /**
         * Generate recommendations based on user data
         */
        suspend fun generateRecommendations(
            userId: String?,
            context: String?,
            config: RecommendationConfig
        ): List<RecommendationItem> {
            // This would integrate with actual ML models and APIs
            return generateMockRecommendations(config)
        }
        
        /**
         * Track user interaction with recommendation
         */
        fun trackInteraction(
            userId: String?,
            item: RecommendationItem,
            action: String
        ) {
            // This would send analytics data
        }
        
        /**
         * Update user preferences based on interactions
         */
        fun updateUserPreferences(
            userId: String?,
            preferences: Map<String, Any>
        ) {
            // This would update user preference model
        }
    }
}