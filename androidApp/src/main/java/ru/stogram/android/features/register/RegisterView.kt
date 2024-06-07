package ru.stogram.android.features.register

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import ru.stogram.android.R
import ru.stogram.android.components.InputTextView
import ru.stogram.android.components.UserAvatarView
import ru.stogram.models.IUser
import ru.stogram.models.UserEntity

@Composable
fun Register() {
    RegisterView(viewModel = hiltViewModel())
}

@Composable
fun RegisterView(
    viewModel: RegisterViewModel,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.title_registration)) },
                navigationIcon = {
                    IconButton(onClick = viewModel::onBackClicked) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "backIcon")
                    }
                },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
                elevation = 10.dp
            )
        }
    ) { paddings ->

        RegisterContentView(
            user = viewModel.randomUser,
            paddingValues =  paddings,
            nameState = viewModel.name,
            loginState = viewModel.login,
            passwordState = viewModel.password,
            passwordRepeatState = viewModel.passwordRepeat,
            onConfirmClicked = viewModel::onConfirmClicked,
            onAddAvatarClicked = {}
        )
    }
}

@Composable
fun RegisterContentView(
    user: IUser,
    paddingValues: PaddingValues,
    nameState: MutableStateFlow<String>,
    loginState: MutableStateFlow<String>,
    passwordState: MutableStateFlow<String>,
    passwordRepeatState: MutableStateFlow<String>,
    onConfirmClicked: () -> Unit,
    onAddAvatarClicked: (IUser) -> Unit
) {
    val name = remember { nameState }
    val login = remember { loginState }
    val password = remember { passwordState }
    val passwordRepeat = remember { passwordRepeatState }
    val focusNameState = remember { mutableStateOf(false) }
    val focusLoginState = remember { mutableStateOf(false) }
    val focusPasswordState = remember { mutableStateOf(false) }
    val focusPasswordRepeatState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            UserAvatarView(user = user, 102.dp, onAddAvatarClicked)
        }

        InputTextView(
            textFlow = name,
            focusState = focusNameState,
            hintResId = R.string.hint_name,
            modifier = Modifier
                .fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            imeAction = ImeAction.Next
        )

        InputTextView(
            textFlow = login,
            focusState = focusLoginState,
            hintResId = R.string.hint_login,
            modifier = Modifier
                .fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            imeAction = ImeAction.Next
        )

        InputTextView(
            textFlow = password,
            focusState = focusPasswordState,
            hintResId = R.string.hint_password,
            modifier = Modifier
                .fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
            imeAction = ImeAction.Next
        )

        InputTextView(
            textFlow = passwordRepeat,
            focusState = focusPasswordRepeatState,
            hintResId = R.string.hint_password_repet,
            modifier = Modifier
                .fillMaxWidth().padding(start = 16.dp, end = 16.dp, bottom = 32.dp),
            imeAction = ImeAction.Done
        ) {
            onConfirmClicked()
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 56.dp),
            onClick = onConfirmClicked
        ) {
            Text(text = stringResource(id = R.string.button_register))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    val user = UserEntity.createRandom()
    val nameState = remember { MutableStateFlow(user.name) }
    val loginState = remember { MutableStateFlow(user.login) }
    val passwordState = remember { MutableStateFlow(user.password) }
    val passwordRepeatState = remember { MutableStateFlow(user.password) }
    RegisterContentView(
        user = UserEntity.createRandom(),
        paddingValues = PaddingValues.Absolute(),
        nameState = nameState,
        loginState = loginState,
        passwordState = passwordState,
        passwordRepeatState = passwordRepeatState,
        onConfirmClicked = {},
        onAddAvatarClicked = {}
    )
}