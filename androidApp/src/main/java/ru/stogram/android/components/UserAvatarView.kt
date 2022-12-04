package ru.stogram.android.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import ru.stogram.android.R
import ru.stogram.android.common.noRippleClickable
import ru.stogram.models.IUser

@Composable
fun UserAvatarView(user: IUser, size: Dp = 48.dp, onClick: ((IUser) -> Unit)? = null) {
    val borderColor =
        if (user.hasStory) {
            Color.Red
        } else {
            Color.DarkGray
        }

    val painter = if (LocalInspectionMode.current) {
        painterResource(id = R.drawable.default_avatar)
    } else {
        val circularProgressDrawable = CircularProgressDrawable(LocalContext.current)
        circularProgressDrawable.strokeWidth = 4f
        circularProgressDrawable.centerRadius = size.value*0.6f

        rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(user.photo)
                .size(Size.ORIGINAL)
                .crossfade(true)
                .placeholder(circularProgressDrawable)
                .listener(onStart = {
                    circularProgressDrawable.start()
                }, onSuccess = { _, _ ->
                    circularProgressDrawable.stop()
                }, onError = { _, _ ->
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
            .size(size)
            .aspectRatio(1f)
            .clip(CircleShape)
            .border(1.dp, borderColor, CircleShape)
            .noRippleClickable {
                onClick?.invoke(user)
            }
    )
}