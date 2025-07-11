package com.xiaomi.base.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import com.xiaomi.base.R
import com.xiaomi.base.domain.model.Item
import com.xiaomi.base.domain.model.ItemStatus
import com.xiaomi.base.ui.component.RatingBadge

/**
 * A card displaying an item.
 *
 * @param item The item to display.
 * @param onClick Callback for when the card is clicked.
 * @param isLarge Whether to display the card in a larger format.
 */
@Composable
fun ItemCard(
    item: Item,
    onClick: () -> Unit,
    isLarge: Boolean = false
) {
    val width = if (isLarge) 180.dp else 120.dp
    val height = if (isLarge) 270.dp else 180.dp
    
    Card(
        modifier = Modifier
            .padding(end = 12.dp, bottom = 4.dp)
            .width(width)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
            ) {
                // Item image
                CoilImage(
                    imageModel = { item.imageUrl },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        alignment = Alignment.Center
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2f / 3f)
                        .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)),
                    failure = {
                        Image(
                            painter = painterResource(R.drawable.placeholder_image),
                            contentDescription = item.title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                )
                
                // Rating badge
                if (item.score > 0) {
                    RatingBadge(
                        rating = item.score,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                    )
                }
            }
            
            // Item title
            Text(
                text = item.title,
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}

@Preview
@Composable
fun ItemCardPreview() {
    val item = Item(
        id = 1,
        title = "Sample Item",
        description = "This is a sample item for preview.",
        imageUrl = "https://example.com/image.jpg",
        thumbnailUrl = "https://example.com/thumbnail.jpg",
        score = 4.5f,
        status = ItemStatus.ACTIVE,
        isFavorite = true,
        tags = listOf("sample", "preview")
    )
    ItemCard(item = item, onClick = {})
}
