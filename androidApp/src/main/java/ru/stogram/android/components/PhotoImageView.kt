package ru.stogram.android.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import ru.stogram.android.R

@Composable
fun PhotoImageView(url: String) {

    val painter = if (LocalInspectionMode.current) {
        painterResource(id = R.drawable.default_avatar)
    } else {
        val circularProgressDrawable = CircularProgressDrawable(LocalContext.current)
        circularProgressDrawable.strokeWidth = 5f
        circularProgressDrawable.centerRadius = 30f

        rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .size(Size.ORIGINAL) // Set the target size to load the image at.
                .crossfade(true)
                .placeholder(circularProgressDrawable)
                .listener(onStart = {
                    circularProgressDrawable.start()
                }, onSuccess = { _, _ ->
                    circularProgressDrawable.stop()
                }, onError = { _, error ->
                    //error.loggE(exception = error.throwable)
                })
                .build(),
            contentScale = ContentScale.Crop
        )
    }

    Image(
        painter = painter,
        contentDescription = "avatar",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    )
}