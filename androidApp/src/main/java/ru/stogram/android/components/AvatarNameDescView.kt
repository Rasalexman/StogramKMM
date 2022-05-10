package ru.stogram.android.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import ru.stogram.android.R
import ru.stogram.android.common.noRippleClickable
import ru.stogram.models.IUser
import ru.stogram.models.UserEntity

class UserPreviewParameterProvider : PreviewParameterProvider<IUser> {
    override val values = sequenceOf(
        UserEntity.createRandom(),
        UserEntity.createRandom(),
    )
}

@Composable
fun AvatarNameDescView(user: IUser, onClick: () -> Unit = {}) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .wrapContentHeight()
            .noRippleClickable(onClick = onClick)
    ) {

        val localModifier =
            if(user.hasStory) {
                Modifier.size(48.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .border(1.dp, Color.Red, CircleShape)
            } else {
                Modifier.size(48.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .border(1.dp, Color.DarkGray, CircleShape)
            }

        val painter = if (LocalInspectionMode.current) {
            painterResource(id = R.drawable.default_avatar)

        } else {
                rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(user.photo)
                        .size(Size.ORIGINAL) // Set the target size to load the image at.
                        .build()
                )
        }

        Image(
            painter = painter,
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,
            modifier = localModifier
        )

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(text = user.name.orEmpty(), fontSize = 16.sp, fontWeight = FontWeight.Bold)
            if(!user.desc.isNullOrEmpty()) {
                Text(user.desc.orEmpty(), fontSize = 12.sp)
            }
        }
    }
}

@Suppress("PreviewAnnotationInFunctionWithParameters")
@Preview("AvatarNameDescViewPreview")
@Composable
fun AvatarNameDescViewPreview(
    @PreviewParameter(UserPreviewParameterProvider::class, limit = 2) user: IUser
) {
    AvatarNameDescView(user = user)
}