package ru.stogram.android.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TopCircleProgressView() {
    Row(modifier = Modifier.fillMaxWidth().padding(top = 48.dp), horizontalArrangement = Arrangement.Center) {
        CircularProgressIndicator(modifier = Modifier.size(48.dp), color = Color.DarkGray, strokeWidth = 4.dp)
    }
}