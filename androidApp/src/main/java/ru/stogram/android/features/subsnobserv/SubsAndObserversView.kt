package ru.stogram.android.features.subsnobserv

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.rememberScaffoldState
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
import ru.stogram.android.components.SimpleLinearProgressIndicator
import ru.stogram.android.constants.ScreenType
import ru.stogram.android.constants.SubsNObsResult
import ru.stogram.android.features.reactions.ReactionItemView
import ru.stogram.android.models.PostItemUI
import ru.stogram.models.IUser

@Composable
fun SubsAndObservers() {
    val vm: SubsAndObserversViewModel = hiltViewModel()
    SubsAndObserversView(viewModel = vm)
}

@Composable
fun SubsAndObserversView(
    viewModel: SubsAndObserversViewModel,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {

    val screenState = viewModel.screenType
    val titleTextRes = if(screenState == ScreenType.OBSERVE) {
        R.string.title_observe
    } else {
        R.string.title_subs
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = titleTextRes))
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
        Column(
            modifier = Modifier
                .padding(paddings)
                .fillMaxSize()
        ) {

            val subsNObservState by viewModel.subsNObservState.collectAsState()
            SubsNObsResultView(
                subNObsState = subsNObservState,
                onAvatarClicked = viewModel::onAvatarClicked
            )
        }
    }
}

@Composable
fun SubsNObsResultView(
    subNObsState: SubsNObsResult,
    onAvatarClicked: (IUser) -> Unit,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        subNObsState.applyIfSuccess { items ->
            items(items = items, key = { it.id }) { user ->
                SubsAndObserverItemView(
                    user = user,
                    onAvatarClicked = onAvatarClicked,
                )

                TabRowDefaults.Divider(
                    color = colorResource(id = R.color.color_light_gray),
                    thickness = 1.dp,
                    modifier = Modifier
                        .padding(start = 8.dp)
                )
            }
        }
    }
}