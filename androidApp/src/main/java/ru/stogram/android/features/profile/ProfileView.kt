package ru.stogram.android.features.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.ui.Scaffold
import com.rasalexman.sresult.common.extensions.applyIfSuccess
import com.rasalexman.sresult.common.extensions.toSuccessResult
import com.rasalexman.sresult.data.dto.SResult
import ru.stogram.android.R
import ru.stogram.android.components.PostImageView
import ru.stogram.android.constants.PostsResult
import ru.stogram.android.features.profile.top.ProfileTopView
import ru.stogram.android.mappers.IPostItemUIMapper
import ru.stogram.android.mappers.PostItemUIMapper
import ru.stogram.android.models.PostItemUI
import ru.stogram.models.IUser
import ru.stogram.models.PostEntity
import ru.stogram.models.UserEntity
import kotlin.math.roundToInt

@Composable
fun Profile() {
    val vm: ProfileViewModel = hiltViewModel()
    ProfileView(viewModel = vm)
}

@Composable
fun ProfileView(viewModel: ProfileViewModel) {
    val postsState by viewModel.postsState.collectAsState()
    val topState by viewModel.userState.collectAsState()
    val toolbarOffsetHeightPx = remember { viewModel.topBarOffset }
    val showTopBar by viewModel.showTopBar.collectAsState()
    val postsCountState by viewModel.postsCountState.collectAsState()

    ProfileView(
        userLogin = viewModel.login,
        topState = topState,
        postsState = postsState,
        postCountState = postsCountState,
        topBarOffset = toolbarOffsetHeightPx,
        showTopBar = showTopBar,
        onPostClicked = viewModel::onPostClicked,
        onBackClicked = viewModel::onBackClicked,
        onMessagesClick = viewModel::onProfileButtonClicked,
        onScreenTypeClick = viewModel::onScreenTypeClick
    )
}

@Composable
internal fun ProfileView(
    userLogin: String,
    topState: SResult<IUser>,
    postCountState: SResult<String>,
    postsState: PostsResult,
    topBarOffset: Float,
    showTopBar: Boolean = false,
    onPostClicked: (PostItemUI) -> Unit,
    onBackClicked: () -> Unit,
    onMessagesClick: (IUser) -> Unit,
    onScreenTypeClick: (String) -> Unit
) {
    val scaffoldState = rememberScaffoldState()

    val toolbarHeight = 184.dp
    val toolbarHeightPx = with(LocalDensity.current) { toolbarHeight.roundToPx().toFloat() }
    val toolbarOffsetHeightPx = remember {
        mutableStateOf(topBarOffset)
    }

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
                        Text(text = userLogin)
                    },
                    navigationIcon = {
                        IconButton(onClick = onBackClicked) {
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

            postsState.applyIfSuccess { posts ->
                LazyVerticalGrid(
                    contentPadding = PaddingValues(top = toolbarHeight),
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(items = posts, key = { it.id }) { post ->
                        PostImageView(post = post, onClick = onPostClicked)
                    }
                }
            }

            if(postsState.isEmpty()) {
                Text(
                    text = stringResource(id = R.string.no_user_post),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = toolbarHeight),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp
                )
            }

            ProfileTopView(
                postsCountState = postCountState,
                userState = topState,
                modifier = Modifier
                    .height(toolbarHeight)
                    .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt()) },
                onProfileButtonClick = onMessagesClick,
                onScreenTypeClick = onScreenTypeClick
            )
        }
    }
}

class ProfilePreviewParameterProvider : PreviewParameterProvider<Pair<PostsResult, SResult<IUser>>> {
    private val postItemUIMapper: IPostItemUIMapper = PostItemUIMapper()
    override val values = sequenceOf(
        PostEntity.createRandomList().map {
            postItemUIMapper.convertSingle(it)
        }.toSuccessResult() to UserEntity.createRandomDetailed(true).toSuccessResult()
    )
}

@Preview
@Composable
fun ProfileViewPreview(
    @PreviewParameter(ProfilePreviewParameterProvider::class, limit = 1) result: Pair<PostsResult, SResult<IUser>>
) {
    ProfileView(
        postCountState = "98721".toSuccessResult(),
        userLogin = "rally.goes",
        topState = result.second,
        postsState = result.first,
        topBarOffset = 0f,
        showTopBar = true,
        onPostClicked = {  },
        onBackClicked = {  },
        onMessagesClick = {},
        onScreenTypeClick = {}
    )
}