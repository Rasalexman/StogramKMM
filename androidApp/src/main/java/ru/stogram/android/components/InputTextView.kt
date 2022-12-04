package ru.stogram.android.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.MutableStateFlow
import ru.stogram.android.R

@Composable
fun InputTextView(
    textFlow: MutableStateFlow<String>,
    focusState: MutableState<Boolean>,
    hintResId: Int,
    modifier: Modifier,
    imeAction: ImeAction? = null,
    onNextClicked: (() -> Unit)? = null,
    onDoneHandler: (() -> Unit)? = null
) {
    val state = textFlow.collectAsState(initial = "")
    TextField(
        value = state.value,
        onValueChange = { value ->
            textFlow.value = value
        },
        placeholder = { Text(stringResource(id = hintResId), color = Color.White.copy(alpha = 0.5f)) },
        modifier = modifier
            .onFocusChanged { fState -> focusState.value = fState.hasFocus },
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        keyboardOptions = imeAction?.let {
            KeyboardOptions(
                imeAction = it
            )
        } ?: KeyboardOptions.Default,
        keyboardActions = onDoneHandler?.let {
            KeyboardActions(onDone = {
                it()
            })
        } ?: onNextClicked?.let {
            KeyboardActions(onNext = {
                it()
            })
        } ?: KeyboardActions(),
        singleLine = true,
        shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
        colors = TextFieldDefaults.textFieldColors(
            textColor = Color.White,
            cursorColor = Color.White,
            leadingIconColor = Color.White,
            trailingIconColor = Color.White,
            backgroundColor = Color.DarkGray,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Preview(showBackground = true)
@Composable
fun InputViewPreview() {
    val textState = remember { MutableStateFlow("") }
    val focusState = remember { mutableStateOf(false) }
    InputTextView(textState, focusState, R.string.hint_login, Modifier
        .fillMaxWidth().padding(PaddingValues(6.dp))) {
    }
}