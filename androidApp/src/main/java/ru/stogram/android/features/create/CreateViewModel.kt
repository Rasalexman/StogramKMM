package ru.stogram.android.features.create

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import ru.stogram.android.features.base.BaseViewModel
import ru.stogram.repository.IPostsRepository
import javax.inject.Inject

@HiltViewModel
class CreateViewModel @Inject constructor(
    private val postsRepository: IPostsRepository
) : BaseViewModel() {

    private var onCreateJob: Job? = null

    private val _postItems: MutableStateFlow<MutableList<ImageUI>> =
        MutableStateFlow(mutableListOf(ImageUI()))
    val postItems: StateFlow<List<ImageUI>> = _postItems
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun onCreateClicked() = launchOnMain {
        postsRepository.addUserPostAsFlow().collect {
            println("NEW POST CREATED = ${it.id}")
        }
    }

    override fun onCleared() {
        onCreateJob = null
        super.onCleared()
    }
}