package ru.stogram.android.features.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel() {

    fun BaseViewModel.launchOnMain( block:suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.Main, CoroutineStart.DEFAULT, block)
    }
}