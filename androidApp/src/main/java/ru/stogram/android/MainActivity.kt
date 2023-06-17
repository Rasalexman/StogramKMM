package ru.stogram.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.core.view.WindowCompat
import androidx.compose.foundation.ExperimentalFoundationApi
import dagger.hilt.android.AndroidEntryPoint
import ru.stogram.android.features.screen.ScreenView
import ru.stogram.android.theme.StogramTheme

@ExperimentalAnimationApi
@ExperimentalTextApi
@OptIn(ExperimentalFoundationApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            StogramTheme(false) {
                ScreenView()
            }
        }
    }
}
