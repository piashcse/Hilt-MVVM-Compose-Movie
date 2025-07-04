package com.xiaomi.base.ui.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.xiaomi.base.domain.model.Item

/**
 * A horizontal carousel of items.
 *
 * @param items The list of items to display in the carousel.
 * @param onItemClick Callback for when an item is clicked.
 * @param isLarge Whether to display the items in a larger format.
 */
@Composable
fun ItemCarousel(
    items: List<Item>,
    onItemClick: (Int) -> Unit,
    isLarge: Boolean = false
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(items) { item ->
            ItemCard(
                item = item,
                onClick = { onItemClick(item.id) },
                isLarge = isLarge
            )
        }
    }
}