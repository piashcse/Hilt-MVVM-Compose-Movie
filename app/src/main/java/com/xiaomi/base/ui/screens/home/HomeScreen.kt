package com.xiaomi.base.ui.screens.home

import android.content.Intent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.xiaomi.base.R
import com.xiaomi.base.templates.demo.DemoActivity
import com.xiaomi.base.ui.component.ErrorView
import com.xiaomi.base.ui.component.ItemCarousel

/**
 * Composable function that displays the home screen.
 *
 * @param navController The navigation controller for handling navigation events.
 * @param viewModel The view model for this screen.
 */
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            uiState.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            uiState.error != null -> {
                ErrorView(
                    message = uiState.error ?: stringResource(R.string.error_unknown),
                    onRetry = { viewModel.loadData() }
                )
            }
            else -> {
                HomeContent(
                    uiState = uiState,
                    navController = navController,
                    onItemClick = { itemId -> viewModel.navigateToItemDetail(navController, itemId) }
                )
            }
        }
    }
}

/**
 * Composable function that displays the content of the home screen.
 *
 * @param uiState The UI state for the home screen.
 * @param navController The navigation controller for handling navigation events.
 * @param onItemClick Callback for when an item is clicked.
 */
@Composable
private fun HomeContent(
    uiState: HomeUiState,
    navController: NavController,
    onItemClick: (Int) -> Unit
) {
    val context = LocalContext.current
    
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        item {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                // Lego Component System Demo Card
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
                            text = "🧱 Lego Component System",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Text(
                            text = "AI-powered component generation system. Describe what you want and let AI create it for you!",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Button(
                            onClick = {
                                val intent = Intent(context, DemoActivity::class.java)
                                context.startActivity(intent)
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("Try Demo")
                        }
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                if (uiState.trendingItems.isNotEmpty()) {
                    Text(
                        text = stringResource(R.string.trending),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    ItemCarousel(
                        items = uiState.trendingItems,
                        onItemClick = onItemClick,
                        isLarge = true
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                }
                
                if (uiState.popularItems.isNotEmpty()) {
                    Text(
                        text = stringResource(R.string.popular),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    ItemCarousel(
                        items = uiState.popularItems,
                        onItemClick = onItemClick
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                }
                
                if (uiState.topRatedItems.isNotEmpty()) {
                    Text(
                        text = stringResource(R.string.top_rated),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    ItemCarousel(
                        items = uiState.topRatedItems,
                        onItemClick = onItemClick
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                }
                
                if (uiState.upcomingItems.isNotEmpty()) {
                    Text(
                        text = stringResource(R.string.upcoming),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    ItemCarousel(
                        items = uiState.upcomingItems,
                        onItemClick = onItemClick
                    )
                }
            }
        }
    }
}