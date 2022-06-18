package ru.stogram.android.features.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.ui.Scaffold
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.sresult.common.extensions.applyIfSuccess
import com.rasalexman.sresult.common.extensions.logg
import com.rasalexman.sresult.common.extensions.toSuccessResult
import com.rasalexman.sresult.data.dto.SResult
import ru.stogram.android.common.rememberStateWithLifecycle
import ru.stogram.android.components.PostImageView
import ru.stogram.android.constants.PostsResult
import ru.stogram.android.features.profile.top.ProfileTopView
import ru.stogram.models.IUser
import ru.stogram.models.PostEntity
import ru.stogram.models.UserEntity
import kotlin.math.roundToInt

@Composable
fun Profile(profileId: String?) {
    profileId?.logg { "CURRENT_PROFILE_ID = ${profileId.orEmpty()}" }
    val showTopBar = profileId != UserEntity.DEFAULT_USER_ID
    val vm: ProfileViewModel by immutableInstance()
    vm.fetchUserProfile(userId = profileId)
    ProfileView(viewModel = vm, showTopBar = showTopBar)
}

@Composable
fun ProfileView(viewModel: ProfileViewModel, showTopBar: Boolean = false) {
    val postsState by rememberStateWithLifecycle(stateFlow = viewModel.postsState)
    val topState by rememberStateWithLifecycle(stateFlow = viewModel.userState)

    ProfileView(
        viewModel = viewModel,
        topState = topState,
        postsState = postsState,
        showTopBar = showTopBar
    )
}

@Composable
internal fun ProfileView(
    viewModel: ProfileViewModel,
    topState: SResult<IUser>,
    postsState: PostsResult,
    showTopBar: Boolean = false
) {
    val scaffoldState = rememberScaffoldState()

    val toolbarHeight = 184.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    val toolbarOffsetHeightPx = remember { viewModel.topBarOffset }

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // try to consume before LazyColumn to collapse toolbar if needed, hence pre-scroll
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                // here's the catch: let's pretend we consumed 0 in any case, since we want
                // LazyColumn to scroll anyway for good UX
                // We're basically watching scroll without taking it
                return Offset.Zero
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if(showTopBar) {
                TopAppBar(
                    title = {
                        Text(text = "Profile App Bar")
                    },
                    navigationIcon = {
                        IconButton(onClick = viewModel::onBackClicked) {
                            Icon(Icons.Filled.ArrowBack, "backIcon")
                        }
                    },
                    backgroundColor = MaterialTheme.colors.primary,
                    contentColor = Color.White,
                    elevation = 10.dp
                )
            }

        }
    ) { paddings ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
                .nestedScroll(nestedScrollConnection)
        ) {

            LazyVerticalGrid(
                contentPadding = PaddingValues(top = toolbarHeight),
                columns = GridCells.Fixed(3),
                modifier = Modifier.fillMaxSize()
            ) {
                postsState.applyIfSuccess { posts ->
                    items(items = posts, key = { it.id }) { post ->
                        PostImageView(post = post, onClick = viewModel::onPostClicked)
                    }
                }
            }

            ProfileTopView(userState = topState, modifier = Modifier
                .height(toolbarHeight)
                .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt()) })
        }
    }
}

class ProfilePreviewParameterProvider : PreviewParameterProvider<Pair<PostsResult, SResult<IUser>>> {
    override val values = sequenceOf(
        PostEntity.createRandomList().toSuccessResult() to UserEntity.createRandomDetailed(true).toSuccessResult()
    )
}

@Preview
@Composable
fun ProfileViewPreview(
    @PreviewParameter(ProfilePreviewParameterProvider::class, limit = 1) result: Pair<PostsResult, SResult<IUser>>
) {
    ProfileView(viewModel = ProfileViewModel(), topState = result.second, postsState = result.first)
}