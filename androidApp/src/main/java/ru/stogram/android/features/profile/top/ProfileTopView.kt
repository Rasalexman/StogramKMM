package ru.stogram.android.features.profile.top

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.sresult.common.extensions.applyIfSuccess
import com.rasalexman.sresult.common.extensions.toSuccessResult
import com.rasalexman.sresult.data.dto.SResult
import ru.stogram.android.R
import ru.stogram.android.common.orZero
import ru.stogram.android.components.TextCountView
import ru.stogram.android.components.UserAvatarView
import ru.stogram.models.IUser
import ru.stogram.models.UserEntity

@Composable
fun ProfileTopView(
    userState: SResult<IUser>,
    modifier: Modifier
) {
    Box(modifier = modifier) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp)) {

            userState.applyIfSuccess { user ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    UserAvatarView(user = user, size = 78.dp)
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        ) {
                            TextCountView(count = user.postCount.orZero(), desc = "Публикации")
                            TextCountView(count = user.subsCount.orZero(), desc = "Подписчики")
                            TextCountView(count = user.observCount.orZero(), desc = "Подписки")
                        }

                        OutlinedButton(
                            onClick = {},
                            shape = RoundedCornerShape(48.dp),
                            border = BorderStroke(2.dp, Color.DarkGray),
                            colors = ButtonDefaults.buttonColors(contentColor = Color.Black, backgroundColor = Color.White)
                        ) {
                            Text(
                                text = "Мои сообщения",
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 30.dp)
                            )
                        }
                    }

                }

                Text(
                    text = user.name.orEmpty(),
                    fontSize = 20.sp,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = user.bio.orEmpty(),
                    fontSize = 12.sp,
                    maxLines = 3,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }

}

class ProfileTopPreviewParameterProvider : PreviewParameterProvider<SResult<IUser>> {
    override val values = sequenceOf(
        UserEntity.createRandomDetailed(true).toSuccessResult()
    )
}

@Preview(showBackground = true)
@Composable
fun ProfileTopViewPreview(
    @PreviewParameter(ProfileTopPreviewParameterProvider::class, limit = 1) result: SResult<IUser>
) {
    ProfileTopView(result, modifier = Modifier
        .fillMaxWidth())
}