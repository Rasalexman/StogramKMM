package ru.stogram.android.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.stogram.android.common.bodyWidth
import ru.stogram.android.common.noRippleClickable
import ru.stogram.models.IUser

@Composable
fun TextCountView(count: String, desc: String, onClick: (() -> Unit)? = null) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(4.dp).noRippleClickable {
            onClick?.invoke()
        }
    ) {
        Text(text = count, fontSize = 16.sp, fontWeight = FontWeight.Bold)
        Text(text = desc, fontSize = 10.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun TextCountViewPreview() {
    TextCountView("100500", "Публикации")
}