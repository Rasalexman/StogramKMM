package ru.stogram.android.features.login

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
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
import com.google.accompanist.insets.ui.Scaffold
import kotlinx.coroutines.flow.MutableStateFlow
import ru.stogram.android.R
import ru.stogram.android.components.InputTextView

@Composable
fun Login() {
    LoginView(viewModel = hiltViewModel())
}

@Composable
fun LoginView(
    viewModel: LoginViewModel,
    scaffoldState: ScaffoldState = rememberScaffoldState()
) {
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.title_login)) },
                backgroundColor = MaterialTheme.colors.primary,
                contentColor = Color.White,
                elevation = 10.dp
            )
        }
    ) {
        LoginViewContent(
            loginState = viewModel.login,
            passwordState = viewModel.password,
            onSignInClicked = viewModel::onSignInClicked,
            onRegisterClicked = viewModel::onRegisterClicked
        )
    }
}

@Composable
fun LoginViewContent(
    loginState: MutableStateFlow<String>,
    passwordState: MutableStateFlow<String>,
    onSignInClicked: () -> Unit,
    onRegisterClicked: () -> Unit = {}
) {

    val login = remember { loginState }
    val password = remember { passwordState }
    val focusLoginState = remember { mutableStateOf(false) }
    val focusPasswordState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InputTextView(
            textFlow = login,
            focusState = focusLoginState,
            hintResId = R.string.hint_login,
            paddingValues = PaddingValues(start = 16.dp, end = 16.dp),
            imeAction = ImeAction.Next
        )

        InputTextView(
            textFlow = password,
            focusState = focusPasswordState,
            hintResId = R.string.hint_password,
            paddingValues = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            imeAction = ImeAction.Done
        ) {
            onSignInClicked()
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 56.dp),
            onClick = onSignInClicked
        ) {
            Text(text = stringResource(id = R.string.button_sign_in))
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 56.dp),
            onClick = onRegisterClicked
        ) {
            Text(text = stringResource(id = R.string.button_open_register))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    val loginState = remember { MutableStateFlow("") }
    val passwordState = remember { MutableStateFlow("") }
    LoginViewContent(
        loginState = loginState,
        passwordState = passwordState,
        onSignInClicked = {},
        onRegisterClicked = {}
    )
}