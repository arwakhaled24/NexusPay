package com.example.nexuspay.feature.send.presentation


import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nexuspay.design.components.OnLoading
import com.example.nexuspay.feature.home.presentation.composable.HomeErrorContent
import com.example.nexuspay.feature.send.presentation.componant.SendMoneyContent
import android.widget.Toast
import kotlinx.coroutines.flow.collectLatest


data class RecentContact(val id: String, val name: String, val image: String)

@Composable
fun SendScreen(
    onNavigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SendViewModel = hiltViewModel(),
) {
    val state by viewModel.viewState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(viewModel) {
        viewModel.singleEvent.collectLatest { event ->
            when (event) {
                is SendContract.Events.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                SendContract.Events.NavigateBack -> onNavigateBack()
            }
        }
    }

    when {
        state.isLoading -> OnLoading(modifier = modifier)
        state.exception != null -> HomeErrorContent(
            message = state.exception!!.message,
            onRetry = { viewModel.processIntent(SendContract.Actions.Retry) },
            modifier = modifier,
        )
        else -> SendMoneyScreen(
            contacts = state.contacts.map { contact ->
                RecentContact(
                    id = contact.id.toString(),
                    name = contact.name,
                    image = contact.avatarUrl,
                )
            },
            amount = state.amount,
            note = state.note,
            selectedContactId = state.selectedUser?.id?.toString(),
            isSending = state.isSending,
            onContactSelected = { contact ->
                state.contacts.firstOrNull { it.id.toString() == contact.id }?.let { user ->
                    viewModel.processIntent(SendContract.Actions.SelectUser(user))
                }
            },
            onNoteChange = { viewModel.processIntent(SendContract.Actions.UpdateNote(it)) },
            onDigit = { viewModel.processIntent(SendContract.Actions.UpdateAmount(it)) },
            onBackspace = { viewModel.processIntent(SendContract.Actions.RemoveAmountDigit) },
            onSend = { viewModel.processIntent(SendContract.Actions.ConfirmSend) },
            modifier = modifier,
        )
    }
}

@Composable
private fun SendMoneyScreen(
    contacts: List<RecentContact>,
    amount: String,
    note: String,
    onContactSelected: (RecentContact) -> Unit,
    onNoteChange: (String) -> Unit,
    onDigit: (String) -> Unit,
    onBackspace: () -> Unit,
    selectedContactId: String?,
    isSending: Boolean,
    onSend: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SendMoneyContent(
        contacts = contacts,
        amount = amount,
        note = note,
        onContactSelected = onContactSelected,
        onNoteChange = onNoteChange,
        onDigit = onDigit,
        onBackspace = onBackspace,
        onSend = onSend,
        modifier = modifier,
        selectedContactId = selectedContactId,
        isSending = isSending,
    )
}
