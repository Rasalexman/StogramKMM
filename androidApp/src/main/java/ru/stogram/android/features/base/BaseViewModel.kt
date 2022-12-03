package ru.stogram.android.features.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rasalexman.sresult.common.extensions.loadingResult
import com.rasalexman.sresult.data.dto.SResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    fun BaseViewModel.launchOnMain( block:suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT, block)
    }

    inline fun<reified T : Any, reified R: Any> Flow<T>.mapIoState(
        sharingStarted: SharingStarted =  SharingStarted.Eagerly,
        crossinline mapBlock: suspend (T) -> SResult<R>
    ): StateFlow<SResult<R>> {
        return this.map(mapBlock).flowOn(Dispatchers.IO)
            .stateIn(viewModelScope, sharingStarted, loadingResult())
    }
}