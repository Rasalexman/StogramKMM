package ru.stogram.android.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.stogram.utils.getRandomPhotoList


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PostContentView(
    postContent: List<String>
) {
    val postContentSize = postContent.size
    val pagerState = rememberPagerState()

    Box(
        Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {

        HorizontalPager(
            pageCount = postContentSize,
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
        ) { page ->
            PhotoImageView(url = postContent.getOrNull(page).orEmpty())
        }

        if (postContentSize > 1) {
            Row(
                Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(postContentSize) { iteration ->
                    val color = if (pagerState.currentPage == iteration) Color.White else Color.DarkGray
                    Box(
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(12.dp)

                    )
                }
            }
        }
    }
}


@Suppress("PreviewAnnotationInFunctionWithParameters")
@Preview(name = "PostContentView", showBackground = true)
@Composable
fun PostContentViewPreview() {
    PostContentView(postContent = getRandomPhotoList())
}