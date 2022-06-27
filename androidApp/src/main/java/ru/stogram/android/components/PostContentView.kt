package ru.stogram.android.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.HorizontalPagerIndicator
import com.google.accompanist.pager.rememberPagerState
import ru.stogram.utils.getRandomPhotoList

@ExperimentalPagerApi
@Composable
fun PostContentView(postContent: List<String>) {

    val postContentSize = postContent.size
    val pagerState = rememberPagerState()

    Box(Modifier.fillMaxWidth().aspectRatio(1f)) {

        HorizontalPager(
            count = postContentSize,
            state = pagerState,
            modifier = Modifier.fillMaxWidth(),
        ) { page ->
            PhotoImageView(url = postContent.getOrNull(page).orEmpty())
        }

        if(postContentSize > 1) {
            HorizontalPagerIndicator(
                pagerState = pagerState,
                activeColor = Color.White,
                inactiveColor = Color.DarkGray,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
            )
        }
    }
}

@ExperimentalPagerApi
@Suppress("PreviewAnnotationInFunctionWithParameters")
@Preview(name = "PostContentView", showBackground = true)
@Composable
fun PostContentViewPreview() {
    PostContentView(postContent = getRandomPhotoList())
}