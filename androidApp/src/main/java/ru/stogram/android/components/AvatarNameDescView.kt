package ru.stogram.android.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
fun AvatarNameDescView(user: IUser, desc: String = "", size: Dp = 48.dp, onClick: () -> Unit = {}) {

    val realDesc = desc.ifEmpty { user.desc }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .wrapContentHeight()
            .noRippleClickable(onClick = onClick)
    ) {

        UserAvatarView(user = user, size = size)

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Text(text = user.name, fontSize = 16.sp, fontWeight = FontWeight.Bold)
            if(realDesc.isNotEmpty()) {
                Text(realDesc, fontSize = 12.sp)
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