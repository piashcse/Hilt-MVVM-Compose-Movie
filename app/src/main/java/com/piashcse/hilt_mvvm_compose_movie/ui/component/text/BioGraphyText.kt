package com.piashcse.hilt_mvvm_compose_movie.ui.component.text

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.piashcse.hilt_mvvm_compose_movie.ui.theme.bioGrapyText

@Composable
fun BioGraphyText(text:String) {
    Text(
        text = text,
        style = MaterialTheme.typography.bioGrapyText
    )
}
