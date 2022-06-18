package ru.stogram.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.core.view.WindowCompat
import com.google.accompanist.pager.ExperimentalPagerApi
import ru.stogram.android.features.MainView
import ru.stogram.android.features.ScreenView
import ru.stogram.android.theme.StogramTheme

@ExperimentalAnimationApi
@ExperimentalTextApi
@ExperimentalPagerApi
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
