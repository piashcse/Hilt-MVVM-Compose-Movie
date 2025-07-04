package com.xiaomi.base.ui.screens.activity

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.xiaomi.base.ui.screens.mainscreen.MainScreen
import com.xiaomi.base.ui.theme.BaseAppTheme
import com.xiaomi.base.preview.registerAllPreviews
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val splashViewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Register all preview demos
        registerAllPreviews()
        
        installSplashScreen().apply {
            setKeepOnScreenCondition { splashViewModel.isLoading.value }
        }
        setContent {
            BaseAppTheme {
                MainScreen()
            }
        }
    }
}