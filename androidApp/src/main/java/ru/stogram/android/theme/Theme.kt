package ru.stogram.android.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.ExperimentalTextApi

@ExperimentalTextApi
@Composable
fun StogramTheme(
    useDarkColors: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (useDarkColors) StogramDarkColors else StogramLightColors,
        typography = StogramTypography,
        shapes = StogramShapes,
        content = content
    )
}
