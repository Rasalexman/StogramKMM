package ru.stogram.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.core.view.WindowCompat
import ru.stogram.android.features.MainView
import ru.stogram.android.theme.StogramTheme

class MainActivity : ComponentActivity() {

    @ExperimentalTextApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            StogramTheme(false) {
                MainView()
            }
        }
    }
}
