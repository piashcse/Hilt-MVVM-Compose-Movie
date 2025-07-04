package com.xiaomi.base.ui.screens.itemdetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import java.text.SimpleDateFormat
import java.util.Locale
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import com.xiaomi.base.R
import com.xiaomi.base.ui.component.ErrorView
import com.xiaomi.base.ui.component.RatingBadge

/**
 * Composable function that displays the item detail screen.
 *
 * @param itemId The ID of the item to display.
 * @param viewModel The view model for this screen.
 */
@Composable
fun ItemDetailScreen(
    itemId: Int,
    viewModel: ItemDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    // Load item details when the screen is first composed
    viewModel.loadItemDetails(itemId)
    
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            uiState.error != null -> {
                ErrorView(
                    message = uiState.error ?: stringResource(R.string.error_unknown),
                    onRetry = { viewModel.loadItemDetails(itemId) }
                )
            }
            uiState.item != null -> {
                ItemDetailContent(
                    item = uiState.item!!,
                    isFavorite = uiState.isFavorite,
                    onFavoriteToggle = { viewModel.toggleFavorite() }
                )
            }
        }
    }
}

/**
 * Composable function that displays the content of the item detail screen.
 *
 * @param item The item to display.
 * @param isFavorite Whether the item is marked as favorite.
 * @param onFavoriteToggle Callback for when the favorite button is clicked.
 */
@Composable
private fun ItemDetailContent(
    item: com.xiaomi.base.domain.model.Item,
    isFavorite: Boolean,
    onFavoriteToggle: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Item Image
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                CoilImage(
                    imageModel = { item.imageUrl },
                    imageOptions = ImageOptions(
                        contentScale = ContentScale.Crop,
                        contentDescription = item.title
                    ),
                    modifier = Modifier.fillMaxSize()
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Title and Rating
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                
                item.rating?.let { rating ->
                    Spacer(modifier = Modifier.width(8.dp))
                    RatingBadge(rating = rating)
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Description
            item.description?.let { description ->
                Text(
                    text = stringResource(R.string.description),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyLarge
                )
                
                Spacer(modifier = Modifier.height(16.dp))
            }
            
            // Release Date
            item.releaseDate?.let { releaseDate ->
                Text(
                    text = stringResource(R.string.release_date),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                Text(
                    text = dateFormat.format(releaseDate),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
        
        // Floating Action Button for Favorite
        FloatingActionButton(
            onClick = onFavoriteToggle,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = if (isFavorite) {
                    stringResource(R.string.remove_from_favorites)
                } else {
                    stringResource(R.string.add_to_favorites)
                }
            )
        }
    }
}