package com.piashcse.hilt_mvvm_compose_movie.ui.component.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.bioGrapyText

@Composable
fun BiographyText(text: String, color: Color = MaterialTheme.colorScheme.onBackground) {
    Text(
        text = text,
        style = MaterialTheme.typography.bioGrapyText,
        color = color // Apply the specified color
    )
}
