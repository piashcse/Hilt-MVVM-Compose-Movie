package com.xiaomi.base.ui.kit.components.navigation.pagination

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.FirstPage
import androidx.compose.material.icons.filled.LastPage
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.xiaomi.base.ui.kit.foundation.XiaomiPreviewTheme
import com.xiaomi.base.ui.kit.foundation.spacing.XiaomiSpacing
import kotlin.math.max
import kotlin.math.min

/**
 * Xiaomi Pagination
 * 
 * A Material Design 3 pagination component with Xiaomi design tokens.
 * 
 * @param currentPage The current page number (1-based)
 * @param totalPages Total number of pages
 * @param onPageChange Callback when page changes
 * @param modifier Modifier to be applied to the pagination
 * @param showFirstLast Whether to show first/last page buttons
 * @param showPrevNext Whether to show previous/next buttons
 * @param maxVisiblePages Maximum number of page buttons to show
 * @param enabled Whether the pagination is enabled
 */
@Composable
fun XiaomiPagination(
    currentPage: Int,
    totalPages: Int,
    onPageChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    showFirstLast: Boolean = true,
    showPrevNext: Boolean = true,
    maxVisiblePages: Int = 5,
    enabled: Boolean = true
) {
    if (totalPages <= 1) return
    
    val pageNumbers = calculateVisiblePages(
        currentPage = currentPage,
        totalPages = totalPages,
        maxVisible = maxVisiblePages
    )
    
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(XiaomiSpacing.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // First page button
        if (showFirstLast && currentPage > 1) {
            IconButton(
                onClick = { onPageChange(1) },
                enabled = enabled
            ) {
                Icon(
                    imageVector = Icons.Default.FirstPage,
                    contentDescription = "First page"
                )
            }
        }
        
        // Previous page button
        if (showPrevNext) {
            IconButton(
                onClick = { onPageChange(max(1, currentPage - 1)) },
                enabled = enabled && currentPage > 1
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronLeft,
                    contentDescription = "Previous page"
                )
            }
        }
        
        // Page numbers
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(XiaomiSpacing.extraSmall)
        ) {
            items(pageNumbers) { pageNumber ->
                when (pageNumber) {
                    -1 -> {
                        // Ellipsis
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .padding(4.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.MoreHoriz,
                                contentDescription = "More pages",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    else -> {
                        XiaomiPageButton(
                            pageNumber = pageNumber,
                            isSelected = pageNumber == currentPage,
                            onClick = { onPageChange(pageNumber) },
                            enabled = enabled
                        )
                    }
                }
            }
        }
        
        // Next page button
        if (showPrevNext) {
            IconButton(
                onClick = { onPageChange(min(totalPages, currentPage + 1)) },
                enabled = enabled && currentPage < totalPages
            ) {
                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Next page"
                )
            }
        }
        
        // Last page button
        if (showFirstLast && currentPage < totalPages) {
            IconButton(
                onClick = { onPageChange(totalPages) },
                enabled = enabled
            ) {
                Icon(
                    imageVector = Icons.Default.LastPage,
                    contentDescription = "Last page"
                )
            }
        }
    }
}

/**
 * Individual page button component
 */
@Composable
private fun XiaomiPageButton(
    pageNumber: Int,
    isSelected: Boolean,
    onClick: () -> Unit,
    enabled: Boolean = true
) {
    val backgroundColor = if (isSelected) {
        MaterialTheme.colorScheme.primary
    } else {
        Color.Transparent
    }
    
    val contentColor = if (isSelected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurface
    }
    
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable(enabled = enabled) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = pageNumber.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = contentColor,
            fontWeight = if (isSelected) FontWeight.Medium else FontWeight.Normal
        )
    }
}

/**
 * Xiaomi Simple Pagination
 * 
 * A simplified pagination with just previous/next buttons and page info.
 * 
 * @param currentPage The current page number (1-based)
 * @param totalPages Total number of pages
 * @param onPageChange Callback when page changes
 * @param modifier Modifier to be applied to the pagination
 * @param showPageInfo Whether to show page information text
 */
@Composable
fun XiaomiSimplePagination(
    currentPage: Int,
    totalPages: Int,
    onPageChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    showPageInfo: Boolean = true
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(XiaomiSpacing.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Previous button
        OutlinedButton(
            onClick = { onPageChange(max(1, currentPage - 1)) },
            enabled = currentPage > 1
        ) {
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = "Previous",
                modifier = Modifier.size(18.dp)
            )
            Text("Previous")
        }
        
        // Page info
        if (showPageInfo) {
            Text(
                text = "Page $currentPage of $totalPages",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        
        // Next button
        OutlinedButton(
            onClick = { onPageChange(min(totalPages, currentPage + 1)) },
            enabled = currentPage < totalPages
        ) {
            Text("Next")
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Next",
                modifier = Modifier.size(18.dp)
            )
        }
    }
}

/**
 * Xiaomi Compact Pagination
 * 
 * A compact pagination for mobile or limited space.
 * 
 * @param currentPage The current page number (1-based)
 * @param totalPages Total number of pages
 * @param onPageChange Callback when page changes
 * @param modifier Modifier to be applied to the pagination
 */
@Composable
fun XiaomiCompactPagination(
    currentPage: Int,
    totalPages: Int,
    onPageChange: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(XiaomiSpacing.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Previous button
        IconButton(
            onClick = { onPageChange(max(1, currentPage - 1)) },
            enabled = currentPage > 1
        ) {
            Icon(
                imageVector = Icons.Default.ChevronLeft,
                contentDescription = "Previous page"
            )
        }
        
        // Current page indicator
        Surface(
            modifier = Modifier.padding(horizontal = XiaomiSpacing.small),
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Text(
                text = "$currentPage / $totalPages",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.padding(
                    horizontal = XiaomiSpacing.medium,
                    vertical = XiaomiSpacing.small
                )
            )
        }
        
        // Next button
        IconButton(
            onClick = { onPageChange(min(totalPages, currentPage + 1)) },
            enabled = currentPage < totalPages
        ) {
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Next page"
            )
        }
    }
}

/**
 * Xiaomi Text Pagination
 * 
 * A text-based pagination with clickable page numbers.
 * 
 * @param currentPage The current page number (1-based)
 * @param totalPages Total number of pages
 * @param onPageChange Callback when page changes
 * @param modifier Modifier to be applied to the pagination
 * @param maxVisiblePages Maximum number of page links to show
 */
@Composable
fun XiaomiTextPagination(
    currentPage: Int,
    totalPages: Int,
    onPageChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    maxVisiblePages: Int = 7
) {
    val pageNumbers = calculateVisiblePages(
        currentPage = currentPage,
        totalPages = totalPages,
        maxVisible = maxVisiblePages
    )
    
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(XiaomiSpacing.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Previous
        if (currentPage > 1) {
            TextButton(
                onClick = { onPageChange(currentPage - 1) }
            ) {
                Text("Previous")
            }
        }
        
        // Page numbers
        pageNumbers.forEach { pageNumber ->
            when (pageNumber) {
                -1 -> {
                    Text(
                        text = "...",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
                else -> {
                    if (pageNumber == currentPage) {
                        Text(
                            text = pageNumber.toString(),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    } else {
                        TextButton(
                            onClick = { onPageChange(pageNumber) }
                        ) {
                            Text(pageNumber.toString())
                        }
                    }
                }
            }
        }
        
        // Next
        if (currentPage < totalPages) {
            TextButton(
                onClick = { onPageChange(currentPage + 1) }
            ) {
                Text("Next")
            }
        }
    }
}

/**
 * Calculate visible page numbers with ellipsis
 */
private fun calculateVisiblePages(
    currentPage: Int,
    totalPages: Int,
    maxVisible: Int
): List<Int> {
    if (totalPages <= maxVisible) {
        return (1..totalPages).toList()
    }
    
    val result = mutableListOf<Int>()
    val halfVisible = maxVisible / 2
    
    when {
        currentPage <= halfVisible + 1 -> {
            // Show first pages
            result.addAll(1..maxVisible - 2)
            result.add(-1) // ellipsis
            result.add(totalPages)
        }
        currentPage >= totalPages - halfVisible -> {
            // Show last pages
            result.add(1)
            result.add(-1) // ellipsis
            result.addAll((totalPages - maxVisible + 3)..totalPages)
        }
        else -> {
            // Show middle pages
            result.add(1)
            result.add(-1) // ellipsis
            result.addAll((currentPage - halfVisible + 2)..(currentPage + halfVisible - 2))
            result.add(-1) // ellipsis
            result.add(totalPages)
        }
    }
    
    return result
}

// Preview composables for design system documentation
@Preview(name = "Xiaomi Pagination - Light")
@Composable
fun XiaomiPaginationPreview() {
    XiaomiPreviewTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    "Pagination Variants",
                    style = MaterialTheme.typography.titleMedium
                )
                
                var currentPage1 by remember { mutableIntStateOf(5) }
                var currentPage2 by remember { mutableIntStateOf(1) }
                var currentPage3 by remember { mutableIntStateOf(3) }
                var currentPage4 by remember { mutableIntStateOf(2) }
                
                // Full pagination
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Full Pagination",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    XiaomiPagination(
                        currentPage = currentPage1,
                        totalPages = 20,
                        onPageChange = { currentPage1 = it }
                    )
                }
                
                // Simple pagination
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Simple Pagination",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    XiaomiSimplePagination(
                        currentPage = currentPage2,
                        totalPages = 10,
                        onPageChange = { currentPage2 = it }
                    )
                }
                
                // Compact pagination
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Compact Pagination",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    XiaomiCompactPagination(
                        currentPage = currentPage3,
                        totalPages = 15,
                        onPageChange = { currentPage3 = it }
                    )
                }
                
                // Text pagination
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Text Pagination",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    XiaomiTextPagination(
                        currentPage = currentPage4,
                        totalPages = 12,
                        onPageChange = { currentPage4 = it }
                    )
                }
            }
        }
    }
}

@Preview(name = "Xiaomi Pagination - Dark")
@Composable
fun XiaomiPaginationDarkPreview() {
    XiaomiPreviewTheme(darkTheme = true) {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    "Dark Theme Pagination",
                    style = MaterialTheme.typography.titleMedium
                )
                
                var currentPage by remember { mutableIntStateOf(3) }
                
                XiaomiPagination(
                    currentPage = currentPage,
                    totalPages = 8,
                    onPageChange = { currentPage = it }
                )
                
                XiaomiSimplePagination(
                    currentPage = currentPage,
                    totalPages = 8,
                    onPageChange = { currentPage = it }
                )
            }
        }
    }
}

@Preview(name = "Xiaomi Pagination - States")
@Composable
fun XiaomiPaginationStatesPreview() {
    XiaomiPreviewTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                Text(
                    "Pagination States",
                    style = MaterialTheme.typography.titleMedium
                )
                
                // First page
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "First Page",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    XiaomiPagination(
                        currentPage = 1,
                        totalPages = 10,
                        onPageChange = {}
                    )
                }
                
                // Last page
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Last Page",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    XiaomiPagination(
                        currentPage = 10,
                        totalPages = 10,
                        onPageChange = {}
                    )
                }
                
                // Disabled
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        "Disabled",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    XiaomiPagination(
                        currentPage = 5,
                        totalPages = 10,
                        onPageChange = {},
                        enabled = false
                    )
                }
            }
        }
    }
}