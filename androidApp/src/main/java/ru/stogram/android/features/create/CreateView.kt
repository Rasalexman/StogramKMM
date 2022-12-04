package ru.stogram.android.features.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ScaffoldState
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
    CreateView(onCreateClicked = vm::onCreateClicked)
}

@Composable
internal fun CreateView(
    onCreateClicked: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
    ) {
        Row(modifier = Modifier.fillMaxSize(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
            Button(onClick = {
                onCreateClicked()
            }) {
                Text(text = "Click to add new Post")
            }
        }
    }
}

@Preview
@Composable
fun CreatePreview() {
    CreateView(onCreateClicked = {})
}