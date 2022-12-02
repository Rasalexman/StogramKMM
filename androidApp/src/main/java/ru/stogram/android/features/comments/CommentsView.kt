package ru.stogram.android.features.comments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.ui.Scaffold
import com.rasalexman.sresult.common.extensions.applyIfLoading
import com.rasalexman.sresult.common.extensions.applyIfSuccess
import ru.stogram.android.R
import ru.stogram.android.components.TopCircleProgressView
import ru.stogram.android.constants.CommentsResult
import ru.stogram.android.models.CommentItemUI
import ru.stogram.models.IUser

@Composable
fun CommentsView() {
    CommentsView(viewModel = hiltViewModel())
}

@Composable
fun CommentsView(
    viewModel: CommentViewModel,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.title_comments))
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

        val commentsState by viewModel.commentsState.collectAsState()
        CommentsView(
            commentsState = commentsState,
            paddingValues = paddings,
            onAvatarClicked = viewModel::onAvatarClicked,
            onLikeClicked = viewModel::onLikeClicked
        )
    }
}

@Composable
fun CommentsView(
    commentsState: CommentsResult,
    paddingValues: PaddingValues,
    onAvatarClicked: (IUser) -> Unit,
    onLikeClicked: (CommentItemUI) -> Unit
) {
    commentsState.applyIfSuccess { items ->
        val topPaddings = paddingValues.calculateTopPadding()
        val bottomPaddings = topPaddings - 8.dp
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = topPaddings, bottom = bottomPaddings)
        ) {
            items(items = items, key = { it.id }) { comment ->
                CommentItemView(
                    comment = comment,
                    onAvatarClicked = onAvatarClicked,
                    onLikeClicked = onLikeClicked
                )

                TabRowDefaults.Divider(
                    color = colorResource(id = R.color.color_light_gray),
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }
        }
    }.applyIfLoading {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(), horizontalArrangement = Arrangement.Center
        ) {
            TopCircleProgressView()
        }
    }
}