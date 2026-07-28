package com.example.nexuspay.core.viewmodel

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface INexusPayViewModel<Action : ViewAction, Event : ViewEvent, State : ViewState> {

    val viewState: StateFlow<State>

    val singleEvent: Flow<Event>

    fun processIntent(action: Action)
}
