package ru.stogram.android.features.create

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun Create() {
    val vm: CreateViewModel = hiltViewModel()
    val imageItems = vm.postItems.collectAsState()
    CreateView(imageItems, vm::onCreateClicked)
}

@Composable
internal fun CreateView(
    imageItems: State<List<ImageUI>>,
    onCreateClicked: () -> Unit,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {

//    val imageUri = remember { mutableStateOf<Uri?>(null) }
//
//    val launcher = rememberLauncherForActivityResult(contract =
//    ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
//        imageUri.value = uri
//    }



    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
    ) { paddings ->
        Column(
            modifier = Modifier
                .padding(paddings)
                .fillMaxSize(),
        ) {

            LazyColumn(modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)) {
                items(
                    items = imageItems.value,
                    key = { it.uri }
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .background(Color.Gray)
                            .padding(4.dp)
                    ) {

                    }
                }
            }

            Row(
                modifier = Modifier
                    .padding(paddings)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Button(onClick = {
                    onCreateClicked()
                    //launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageAndVideo))
                }) {
                    Text(text = "Click to add new Post")
                }
            }

        }

    }
}

@Preview
@Composable
fun CreatePreview() {
    CreateView(MutableStateFlow(mutableListOf(ImageUI())).collectAsState(), {})
}