package com.softspire.core_presentation.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

fun <State> MutableStateFlow<State>.toViewModelState(
    viewModelScope: CoroutineScope,
    loadData: () -> Unit
): StateFlow<State> {
    return this@toViewModelState.onStart {
        loadData()
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000L),
        value
    )
}