package ru.stogram.android.features.create

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.insets.ui.Scaffold
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.rasalexman.kodi.core.immutableInstance
import ru.stogram.android.common.Layout
import ru.stogram.android.common.bodyWidth

@Composable
fun Create() {
    val vm: CreateViewModel by immutableInstance()
    CreateView(viewModel = vm)
}

@Composable
internal fun CreateView(
    viewModel: CreateViewModel
) {
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Button(onClick = {
                viewModel.onCreateClicked()
            }) {
                Text(text = "Click to add new Post")
            }
        }
    }
}

@Preview
@Composable
fun SearchPreview() {
    CreateView(viewModel = CreateViewModel())
}