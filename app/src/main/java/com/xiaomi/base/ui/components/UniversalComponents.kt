package com.xiaomi.base.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xiaomi.base.domain.model.Item
import com.xiaomi.base.domain.model.ItemStatus
import com.xiaomi.base.ui.theme.*

/**
 * Universal UI Components for any type of app.
 * These components can be adapted for various domains and use cases.
 */

// ===== Data Visualization Components =====

/**
 * Universal metric card that displays a key metric with optional trend
 */
@Composable
fun MetricCard(
    title: String,
    value: String,
    unit: String? = null,
    trend: TrendDirection? = null,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                if (unit != null) {
                    Text(
                        text = " $unit",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(start = 4.dp, bottom = 2.dp)
                    )
                }
                
                trend?.let { trendDirection ->
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        imageVector = when (trendDirection) {
                            TrendDirection.UP -> Icons.Default.TrendingUp
                            TrendDirection.DOWN -> Icons.Default.TrendingDown
                            TrendDirection.NEUTRAL -> Icons.Default.TrendingFlat
                        },
                        contentDescription = null,
                        tint = when (trendDirection) {
                            TrendDirection.UP -> Color(0xFF4CAF50)
                            TrendDirection.DOWN -> Color(0xFFF44336)
                            TrendDirection.NEUTRAL -> MaterialTheme.colorScheme.onSurfaceVariant
                        },
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

/**
 * Universal progress chart component
 */
@Composable
fun ProgressChart(
    data: List<ChartDataPoint>,
    chartType: ChartType = ChartType.LINE,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Progress Chart",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Simple visualization placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${chartType.name} Chart\n${data.size} data points",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

/**
 * Universal stats grid component
 */
@Composable
fun StatsGrid(
    stats: List<StatItem>,
    columns: Int = 2,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(stats) { stat ->
            MetricCard(
                title = stat.label,
                value = stat.value,
                unit = stat.unit,
                trend = stat.trend
            )
        }
    }
}

// ===== Content Display Components =====

/**
 * Universal card component that can display any item
 */
@Composable
fun UniversalCard(
    item: Item,
    displayMode: CardDisplayMode = CardDisplayMode.STANDARD,
    modifier: Modifier = Modifier,
    onItemClick: (Item) -> Unit = {},
    onFavoriteClick: (Item) -> Unit = {}
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onItemClick(item) },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        when (displayMode) {
            CardDisplayMode.COMPACT -> CompactCardContent(item, onFavoriteClick)
            CardDisplayMode.STANDARD -> StandardCardContent(item, onFavoriteClick)
            CardDisplayMode.DETAILED -> DetailedCardContent(item, onFavoriteClick)
        }
    }
}

@Composable
private fun CompactCardContent(
    item: Item,
    onFavoriteClick: (Item) -> Unit
) {
    Row(
        modifier = Modifier.padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Image placeholder
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = RoundedCornerShape(8.dp)
                )
        )
        
        Spacer(modifier = Modifier.width(12.dp))
        
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            
            item.description?.let { desc ->
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
        
        IconButton(onClick = { onFavoriteClick(item) }) {
            Icon(
                imageVector = if (item.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Toggle favorite",
                tint = if (item.isFavorite) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun StandardCardContent(
    item: Item,
    onFavoriteClick: (Item) -> Unit
) {
    Column {
        // Image placeholder
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        )
        
        Column(
            modifier = Modifier.padding(12.dp)
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
                    
                    item.description?.let { desc ->
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = desc,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                
                IconButton(onClick = { onFavoriteClick(item) }) {
                    Icon(
                        imageVector = if (item.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "Toggle favorite",
                        tint = if (item.isFavorite) Color.Red else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Score badge
                if (item.score > 0) {
                    Badge {
                        Text(text = "★ ${item.score}")
                    }
                }
                
                // Status chip
                StatusChip(status = item.status)
            }
        }
    }
}

@Composable
private fun DetailedCardContent(
    item: Item,
    onFavoriteClick: (Item) -> Unit
) {
    Column {
        StandardCardContent(item, onFavoriteClick)
        
        // Additional details
        if (item.tags.isNotEmpty()) {
            LazyRow(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(item.tags) { tag ->
                    FilterChip(
                        onClick = { },
                        label = { Text(tag) },
                        selected = false
                    )
                }
            }
        }
    }
}

/**
 * Universal media viewer component
 */
@Composable
fun MediaViewer(
    mediaUrl: String,
    mediaType: MediaType,
    modifier: Modifier = Modifier,
    onEdit: (() -> Unit)? = null
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box {
            // Media content placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = when (mediaType) {
                            MediaType.IMAGE -> Icons.Default.Image
                            MediaType.VIDEO -> Icons.Default.PlayArrow
                            MediaType.AUDIO -> Icons.Default.AudioFile
                            MediaType.DOCUMENT -> Icons.Default.Description
                        },
                        contentDescription = null,
                        modifier = Modifier.size(48.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "${mediaType.name} Viewer",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            
            // Edit button if available
            onEdit?.let { editAction ->
                FloatingActionButton(
                    onClick = editAction,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp),
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit"
                    )
                }
            }
        }
    }
}

// ===== Helper Components =====

@Composable
fun StatusChip(
    status: ItemStatus,
    modifier: Modifier = Modifier
) {
    val (color, label) = when (status) {
        ItemStatus.ACTIVE -> Color(0xFF4CAF50) to "Active"
        ItemStatus.INACTIVE -> Color(0xFF9E9E9E) to "Inactive"
        ItemStatus.DRAFT -> Color(0xFF2196F3) to "Draft"
        ItemStatus.ARCHIVED -> Color(0xFF795548) to "Archived"
        ItemStatus.DELETED -> Color(0xFFF44336) to "Deleted"
    }
    
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        color = color.copy(alpha = 0.1f),
        border = BorderStroke(1.dp, color.copy(alpha = 0.3f))
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
}

// ===== Data Classes =====

enum class TrendDirection {
    UP, DOWN, NEUTRAL
}

enum class ChartType {
    LINE, BAR, PIE, AREA
}

enum class CardDisplayMode {
    COMPACT, STANDARD, DETAILED
}

enum class MediaType {
    IMAGE, VIDEO, AUDIO, DOCUMENT
}

data class ChartDataPoint(
    val label: String,
    val value: Float,
    val timestamp: Long? = null
)

data class StatItem(
    val label: String,
    val value: String,
    val unit: String? = null,
    val trend: TrendDirection? = null
) 