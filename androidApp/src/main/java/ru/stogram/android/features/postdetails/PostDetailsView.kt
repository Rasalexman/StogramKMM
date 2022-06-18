package ru.stogram.android.features.postdetails

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.pager.ExperimentalPagerApi
import com.rasalexman.kodi.core.immutableInstance
import com.rasalexman.sresult.common.extensions.applyIfSuccess
import com.rasalexman.sresult.common.extensions.logg
import ru.stogram.android.common.orZero
import ru.stogram.android.common.rememberStateWithLifecycle
import ru.stogram.android.components.AvatarNameDescView
import ru.stogram.android.components.CommentsView
import ru.stogram.android.components.LikesView
import ru.stogram.android.components.PostContentView
import ru.stogram.android.features.profile.ProfileViewModel
import ru.stogram.models.PostEntity
import ru.stogram.models.UserEntity
import ru.stogram.utils.getRandomString

@ExperimentalPagerApi
@Composable
fun PostDetailsView(postId: String?) {

    postId?.logg { "CURRENT_POST_ID = ${postId.orEmpty()}" }
    val vm: PostDetailsViewModel by immutableInstance()
    vm.fetchSelectedPost(postId)

    PostDetailsView(vm)
}

@ExperimentalPagerApi
@Composable
fun PostDetailsView(
    viewModel: PostDetailsViewModel
) {
    val scaffoldState = rememberScaffoldState()
    val postState by rememberStateWithLifecycle(stateFlow = viewModel.postState)

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    postState.applyIfSuccess { post ->
                        if(post.takePostUser().id != UserEntity.DEFAULT_USER_ID) {
                            AvatarNameDescView(user = post.takePostUser(), size = 32.dp)
                        }
                    }
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
    ) { paddings ->
        postState.applyIfSuccess {
            PostDetailsView(it, paddings)
        }
    }
}

@ExperimentalPagerApi
@Composable
fun PostDetailsView(
    post: PostEntity,
    paddingValues: PaddingValues
) {
    var isLikedState by remember { mutableStateOf(post.isLiked) }
    val postPhotos: List<String> by post.takeContentFlow().collectAsState(
        initial = emptyList()
    )

    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .padding(paddingValues)
        .padding(bottom = 8.dp)) {

        item {
            PostContentView(postPhotos)
        }

        item {
            Row(modifier = Modifier.padding(all = 8.dp)) {
                LikesView(count = post.likesCount.orZero(), isSelected = isLikedState) {
                    //post.isLiked = !post.isLiked
                    isLikedState = post.isLiked
                }

                Spacer(modifier = Modifier.padding(start = 16.dp))

                CommentsView(count = post.commentsCount.orZero()) {

                }
            }
        }

        item {
            Text(
                text = getRandomString(3000),
                modifier = Modifier.padding(horizontal = 8.dp),
                fontSize = 14.sp
            )
        }
    }
}