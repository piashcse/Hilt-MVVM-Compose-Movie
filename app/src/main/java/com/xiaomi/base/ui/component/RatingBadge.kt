package com.xiaomi.base.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A circular badge displaying a rating value.
 *
 * @param rating The rating value to display.
 * @param modifier Modifier to be applied to the badge.
 */
@Composable
fun RatingBadge(
    rating: Float,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when {
        rating >= 8.0f -> Color(0xFF388E3C) // Green for high ratings
        rating >= 6.0f -> Color(0xFFFFA000) // Amber for medium ratings
        else -> Color(0xFFD32F2F) // Red for low ratings
    }
    
    Box(
        modifier = modifier
            .size(36.dp)
            .clip(CircleShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = String.format("%.1f", rating),
            color = Color.White,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(2.dp)
        )
    }
}