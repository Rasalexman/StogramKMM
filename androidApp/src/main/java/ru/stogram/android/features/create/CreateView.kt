package ru.stogram.android.features.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.ui.Scaffold

@Composable
fun Create() {
    val vm: CreateViewModel = hiltViewModel()
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
    CreateView(viewModel = hiltViewModel())
}