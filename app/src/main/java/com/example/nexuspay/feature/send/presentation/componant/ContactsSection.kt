package com.example.nexuspay.feature.send.presentation.componant
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.unit.dp
import com.example.nexuspay.feature.send.presentation.RecentContact

@Composable
 fun ContactsSection(
    contacts: List<RecentContact>,
    selectedContactId: String?,
    onContactSelected: (RecentContact) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "RECENT CONTACTS",
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(start = 4.dp),
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items(contacts.size) { index ->
                ContactItem(
                    contact = contacts[index],
                    selected = contacts[index].id == selectedContactId,
                    onClick = { onContactSelected(contacts[index]) },
                )
            }
        }
    }
}