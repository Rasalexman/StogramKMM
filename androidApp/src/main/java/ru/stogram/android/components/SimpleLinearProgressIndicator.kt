package ru.stogram.android.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SimpleLinearProgressIndicator() {
    LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
}