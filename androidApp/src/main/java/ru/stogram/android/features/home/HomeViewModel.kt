package ru.stogram.android.features.home

import androidx.lifecycle.ViewModel
import com.rasalexman.kodi.annotations.BindSingle
import com.rasalexman.sresult.common.extensions.emptyResult
import com.rasalexman.sresult.data.dto.SResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ru.stogram.android.di.ModuleNames
import ru.stogram.models.PostEntity

@BindSingle(
    toClass = HomeViewModel::class,
    toModule = ModuleNames.ViewModels
)
class HomeViewModel : ViewModel() {

    val resultState: StateFlow<SResult<List<PostEntity>>> = MutableStateFlow(emptyResult())
    val refreshing: Boolean = false

    fun onSwipeRefresh() {

    }
}