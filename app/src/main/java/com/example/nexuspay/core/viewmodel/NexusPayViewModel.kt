package com.example.nexuspay.core.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class NexusPayViewModel<Action : ViewAction, Event : ViewEvent, State : ViewState>(
    initialState: State,
) : INexusPayViewModel<Action, Event, State>, ViewModel() {

    private val _viewState: MutableStateFlow<State> = MutableStateFlow(initialState)
    override val viewState: StateFlow<State> get() = _viewState.asStateFlow()

    private val eventChannel = Channel<Event>(Channel.UNLIMITED)
    override val singleEvent: Flow<Event> = eventChannel.receiveAsFlow()

    private val _actionFlow = MutableSharedFlow<Action>(extraBufferCapacity = 64)
    protected val actionSharedFlow: SharedFlow<Action> = _actionFlow.asSharedFlow()

    val oldViewState: State get() = _viewState.value

    final override fun processIntent(action: Action) {
        check(_actionFlow.tryEmit(action)) { "Failed to emit action: $action" }
    }

    protected fun sendEvent(event: Event) {
        eventChannel.trySend(event)
    }

    fun setState(newState: State) {
        _viewState.value = newState
    }

    protected abstract fun onActionTrigger(action: Action)

    abstract fun clearState()

    init {
        viewModelScope.launch {
            actionSharedFlow.collect { onActionTrigger(it) }
        }
    }
}
