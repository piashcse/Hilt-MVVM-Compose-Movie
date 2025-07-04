package com.xiaomi.base.preview.catalog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.xiaomi.base.ui.theme.BaseTheme

/**
 * Activity for displaying demo content as standalone screens
 * without any preview wrapper or UI
 */
class DemoActivity : ComponentActivity() {
    companion object {
        private const val EXTRA_PREVIEW_ID = "preview_id"
        
        fun createIntent(context: Context, previewId: String): Intent {
            return Intent(context, DemoActivity::class.java).apply {
                putExtra(EXTRA_PREVIEW_ID, previewId)
            }
        }
    }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        val previewId = intent.getStringExtra(EXTRA_PREVIEW_ID)
        if (previewId == null) {
            finish()
            return
        }
        
        val previewItem = PreviewRegistry.getPreview(previewId)
        if (previewItem == null) {
            finish()
            return
        }
        
        setContent {
            BaseTheme {
                // Render the preview content directly as a standalone screen
                previewItem.content()
            }
        }
    }
} 