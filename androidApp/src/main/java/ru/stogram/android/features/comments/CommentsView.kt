package ru.stogram.android.features.comments

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
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
import ru.stogram.android.common.rememberStateWithLifecycle
import ru.stogram.android.components.TopCircleProgressView
import ru.stogram.android.constants.CommentsResult
import ru.stogram.models.CommentEntity

@Composable
fun CommentsView() {
    val vm: CommentViewModel = hiltViewModel()
    CommentsView(vm)
}

@Composable
fun CommentsView(viewModel: CommentViewModel) {
    val scaffoldState = rememberScaffoldState()
    val commentsState by rememberStateWithLifecycle(stateFlow = viewModel.commentsState)

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
        CommentsView(
            commentsState = commentsState,
            paddingValues = paddings,
            onAvatarClicked = viewModel::onAvatarClicked
        )
    }
}

@Composable
fun CommentsView(
    commentsState: CommentsResult,
    paddingValues: PaddingValues,
    onAvatarClicked: (CommentEntity) -> Unit
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
                    onAvatarClicked = { }
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