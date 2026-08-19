package com.example.nexuspay.feature.send.presentation.componant
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import com.example.nexuspay.feature.send.presentation.RecentContact

@Composable
fun SendMoneyContent(
    contacts: List<RecentContact>,
    amount: String,
    note: String,
    selectedContactId: String?,
    isSending: Boolean,
    onContactSelected: (RecentContact) -> Unit,
    onNoteChange: (String) -> Unit,
    onDigit: (String) -> Unit,
    onBackspace: () -> Unit,
    onSend: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
        SearchBar(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        )

        ContactsSection(
            contacts = contacts,
            selectedContactId = selectedContactId,
            onContactSelected = onContactSelected,
        )

        TransferAmountSection(
            amount = amount,
            note = note,
            onNoteChange = onNoteChange,
        )

        NumericKeypad(
            onDigit = onDigit,
            onBackspace = onBackspace,
            modifier = Modifier.fillMaxWidth(),
        )

        ConfirmSection(
            onSend = onSend,
            isSending = isSending,
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(Modifier.height(8.dp))
    }
}