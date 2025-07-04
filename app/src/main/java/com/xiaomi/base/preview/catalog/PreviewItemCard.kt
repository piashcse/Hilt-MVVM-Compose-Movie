package com.xiaomi.base.preview.catalog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.tooling.preview.Preview

/**
 * Card component for displaying preview items in both grid and list layouts
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PreviewItemCard(
    item: PreviewItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isCompact: Boolean = false
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp,
            pressedElevation = 8.dp
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        if (isCompact) {
            CompactCardContent(item = item)
        } else {
            ExpandedCardContent(item = item)
        }
    }
}

@Preview
@Composable
fun PreviewItemCardPreview() {
    val item = PreviewItem(
        id = "1",
        title = "Sample Preview Item",
        description = "This is a sample description for the preview item.",
        category = PreviewCategory.AI,
        icon = Icons.Default.Psychology,
        tags = listOf("Android", "Compose"),
        difficulty = PreviewDifficulty.BEGINNER,
        estimatedTime = "10 min",
        content = {}
    )
    PreviewItemCard(
        item = item,
        onClick = {},
        isCompact = false
    )
}

/**
 * Compact card content for grid view
 */
@Composable
fun CompactCardContent(item: PreviewItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Header with category icon and difficulty
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = item.category.icon,
                contentDescription = item.category.displayName,
                modifier = Modifier.size(20.dp),
                tint = Color(item.category.color)
            )
            
            DifficultyBadge(
                difficulty = item.difficulty,
                isCompact = true
            )
        }
        
        // Title
        Text(
            text = item.title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        
        // Description
        Text(
            text = item.description,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 3,
            overflow = TextOverflow.Ellipsis
        )
        
        // Footer with tags and estimated time
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            if (item.tags.isNotEmpty()) {
                TagRow(
                    tags = item.tags.take(2),
                    isCompact = true
                )
            }
            
            if (item.estimatedTime.isNotEmpty()) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Schedule,
                        contentDescription = "Estimated time",
                        modifier = Modifier.size(12.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = item.estimatedTime,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun CompactCardContentPreview() {
    val item = PreviewItem(
        id = "1",
        title = "Sample Preview Item",
        description = "This is a sample description for the preview item. It can be quite long and should be truncated properly.",
        category = PreviewCategory.CREATIVE,
        icon = Icons.Default.Palette,
        tags = listOf("UI", "UX", "Design"),
        difficulty = PreviewDifficulty.INTERMEDIATE,
        estimatedTime = "15 min",
        content = {}
    )
    CompactCardContent(item = item)
}

/**
 * Expanded card content for list view
 */
@Composable
fun ExpandedCardContent(item: PreviewItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Left side - Icon and category
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                color = Color(item.category.color).copy(alpha = 0.1f)
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = item.category.icon,
                        contentDescription = item.category.displayName,
                        modifier = Modifier.size(24.dp),
                        tint = Color(item.category.color)
                    )
                }
            }
            
            Text(
                text = item.category.displayName,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        // Right side - Content
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Header with title and difficulty
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                DifficultyBadge(
                    difficulty = item.difficulty,
                    isCompact = false
                )
            }
            
            // Description
            Text(
                text = item.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            
            // Tags
            if (item.tags.isNotEmpty()) {
                TagRow(
                    tags = item.tags,
                    isCompact = false
                )
            }
            
            // Footer with metadata
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (item.estimatedTime.isNotEmpty()) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Schedule,
                            contentDescription = "Estimated time",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text = item.estimatedTime,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "View details",
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview
@Composable
fun ExpandedCardContentPreview() {
    val item = PreviewItem(
        id = "1",
        title = "Expanded Sample Preview Item with a Very Long Title That Might Overflow",
        description = "This is a detailed description for the expanded preview item, showcasing how text behaves in a list view. It should handle multiple lines gracefully.",
        category = PreviewCategory.HEALTH,
        icon = Icons.Default.FavoriteBorder,
        tags = listOf("Fitness", "Well-being", "Lifestyle", "Health"),
        difficulty = PreviewDifficulty.ADVANCED,
        estimatedTime = "20 min",
        content = {}
    )
    ExpandedCardContent(item = item)
}


/**
 * Badge component for displaying difficulty level
 */
@Composable
fun DifficultyBadge(
    difficulty: PreviewDifficulty,
    isCompact: Boolean = false
) {
    val (backgroundColor, contentColor) = when (difficulty) {
        PreviewDifficulty.BEGINNER -> 
            MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.onPrimaryContainer
        PreviewDifficulty.INTERMEDIATE -> 
            MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
        PreviewDifficulty.ADVANCED -> 
            MaterialTheme.colorScheme.tertiaryContainer to MaterialTheme.colorScheme.onTertiaryContainer
    }
    
    Surface(
        shape = RoundedCornerShape(if (isCompact) 6.dp else 8.dp),
        color = backgroundColor
    ) {
        Text(
            text = difficulty.displayName,
            style = if (isCompact) MaterialTheme.typography.labelSmall else MaterialTheme.typography.labelMedium,
            color = contentColor,
            modifier = Modifier.padding(
                horizontal = if (isCompact) 6.dp else 8.dp,
                vertical = if (isCompact) 2.dp else 4.dp
            )
        )
    }
}

@Preview
@Composable
fun DifficultyBadgePreview() {
    DifficultyBadge(difficulty = PreviewDifficulty.INTERMEDIATE, isCompact = false)
}


/**
 * Row of tags with proper wrapping and styling
 */
@Composable
fun TagRow(
    tags: List<String>,
    isCompact: Boolean = false,
    maxTags: Int = if (isCompact) 2 else 4
) {
    val displayTags = tags.take(maxTags)
    val remainingCount = tags.size - displayTags.size
    
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        displayTags.forEach { tag ->
            TagChip(
                text = tag,
                isCompact = isCompact
            )
        }
        
        if (remainingCount > 0) {
            Text(
                text = "+$remainingCount",
                style = if (isCompact) MaterialTheme.typography.labelSmall else MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview
@Composable
fun TagRowPreview() {
    TagRow(tags = listOf("Android", "Compose", "Kotlin", "UI", "UX"), isCompact = false)
}

/**
 * Individual tag chip component
 */
@Composable
fun TagChip(
    text: String,
    isCompact: Boolean = false
) {
    Surface(
        shape = RoundedCornerShape(if (isCompact) 4.dp else 6.dp),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Text(
            text = text,
            style = if (isCompact) MaterialTheme.typography.labelSmall else MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(
                horizontal = if (isCompact) 4.dp else 6.dp,
                vertical = if (isCompact) 1.dp else 2.dp
            )
        )
    }
}

@Preview
@Composable
fun TagChipPreview() {
    TagChip(text = "Android", isCompact = false)
}