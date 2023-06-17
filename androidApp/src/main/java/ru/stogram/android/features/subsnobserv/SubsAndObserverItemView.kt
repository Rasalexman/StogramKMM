package ru.stogram.android.features.subsnobserv

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.stogram.android.components.AvatarNameDescView
import ru.stogram.models.IUser

@Composable
fun SubsAndObserverItemView(
    user: IUser,
    onAvatarClicked: (IUser) -> Unit,
) {

    Row(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 8.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        AvatarNameDescView(user = user) {
            onAvatarClicked.invoke(user)
        }
    }
}