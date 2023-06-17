package ru.stogram.android.features.profile.top

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rasalexman.sresult.common.extensions.applyIfSuccess
import com.rasalexman.sresult.common.extensions.toSuccessResult
import com.rasalexman.sresult.data.dto.SResult
import ru.stogram.android.R
import ru.stogram.android.common.orZero
import ru.stogram.android.components.TextCountView
import ru.stogram.android.components.UserAvatarView
import ru.stogram.android.constants.ScreenType
import ru.stogram.models.IUser
import ru.stogram.models.UserEntity

@Composable
fun ProfileTopView(
    postsCountState: SResult<String>,
    userState: SResult<IUser>,
    modifier: Modifier,
    onProfileButtonClick: (IUser) -> Unit,
    onScreenTypeClick: (String) -> Unit
) {

    val postCountText = postsCountState.data ?: "-"

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
        ) {

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

                            TextCountView(
                                count = postCountText,
                                desc = stringResource(id = R.string.title_publications)
                            ) {

                            }
                            TextCountView(
                                count = user.subsCount.orZero(),
                                desc = stringResource(id = R.string.title_subs)
                            ) {
                                onScreenTypeClick(ScreenType.SUBS)
                            }
                            TextCountView(
                                count = user.observCount.orZero(),
                                desc = stringResource(id = R.string.title_observe)
                            ) {
                                onScreenTypeClick(ScreenType.OBSERVE)
                            }
                        }

                        val buttonTextRes = if (user.isCurrentUser) {
                            R.string.button_exit
                        } else {
                            if (!user.isSubscribed) {
                                R.string.button_subscribe
                            } else {
                                R.string.button_unsubscribe
                            }

                        }

                        OutlinedButton(
                            onClick = {
                                onProfileButtonClick.invoke(user)
                            },
                            shape = RoundedCornerShape(48.dp),
                            border = BorderStroke(2.dp, Color.DarkGray),
                            colors = ButtonDefaults.buttonColors(
                                contentColor = Color.Black,
                                backgroundColor = Color.White
                            )
                        ) {
                            Text(
                                text = stringResource(id = buttonTextRes),
                                fontSize = 12.sp,
                                modifier = Modifier.padding(horizontal = 30.dp)
                            )
                        }
                    }

                }

                Text(
                    text = user.name,
                    fontSize = 20.sp,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = user.bio,
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
    ProfileTopView(
        postsCountState = "34".toSuccessResult(),
        userState = result,
        modifier = Modifier.fillMaxWidth(),
        onProfileButtonClick = {},
        onScreenTypeClick = { _ -> }
    )
}