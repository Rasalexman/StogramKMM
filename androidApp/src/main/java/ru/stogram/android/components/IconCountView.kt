package ru.stogram.android.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.stogram.android.common.noRippleClickable
import ru.stogram.utils.randomBool
import ru.stogram.utils.randomCount

@Composable
fun LikesView(
    count: String = "0",
    isSelected: Boolean = false,
    iconSize: Dp = 36.dp,
    textSize: TextUnit = 16.sp,
    onClick: () -> Unit
) {
    IconCountView(
        count = count,
        isSelected = isSelected,
        normalIcon = Icons.Default.FavoriteBorder,
        selectedIcon = Icons.Default.Favorite,
        iconSize = iconSize,
        textSize = textSize,
        onClick = onClick
    )
}

@Composable
fun CommentsView(
    count: String = "0",
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    IconCountView(
        count = count,
        isSelected = isSelected,
        normalIcon = Icons.Default.Comment,
        onClick = onClick
    )
}

@Composable
fun IconCountView(
    count: String = "0",
    isSelected: Boolean = false,
    normalIcon: ImageVector = Icons.Default.FavoriteBorder,
    selectedIcon: ImageVector? = null,
    iconSize: Dp = 36.dp,
    textSize: TextUnit = 16.sp,
    onClick: () -> Unit
) {
    val painter = if(isSelected && selectedIcon != null) {
        rememberVectorPainter(selectedIcon)
    } else {
        rememberVectorPainter(normalIcon)
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .wrapContentHeight()
            .noRippleClickable(onClick = onClick)
    ) {
        Icon(
            painter = painter,
            contentDescription = "IconCountView",
            modifier = Modifier.size(iconSize)
        )
        Text(
            text = count,
            modifier = Modifier.padding(all = 4.dp),
            fontSize = textSize
        )
    }
}

@ExperimentalMaterialApi
@Preview(name = "IconCountViewPreview", showBackground = true)
@Composable
fun IconCountViewPreview() {
    IconCountView(
        count = randomCount,
        isSelected = randomBool,
        normalIcon = Icons.Default.Comment,
        selectedIcon = Icons.Default.Favorite
    ) {

    }
}