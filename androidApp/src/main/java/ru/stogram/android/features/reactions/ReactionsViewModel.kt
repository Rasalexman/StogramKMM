package ru.stogram.android.features.reactions

import androidx.lifecycle.ViewModel
import com.rasalexman.kodi.annotations.BindSingle
import ru.stogram.android.di.ModuleNames

@BindSingle(
    toClass = ReactionsViewModel::class,
    toModule = ModuleNames.ViewModels
)
class ReactionsViewModel : ViewModel() {

    val refreshing: Boolean = false

    fun onSwipeRefresh() {

    }
}