package com.example.nexuspay.feature.transactions.presentation

import android.content.ClipData
import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nexuspay.feature.transactions.presentation.componant.TransactionHistoryContent
import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.nexuspay.feature.transactions.domain.model.TransactionListItem
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TransactionsScreen(
    modifier: Modifier = Modifier,
    viewModel: TransactionsViewModel = hiltViewModel(),
) {
    val state = viewModel.viewState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.singleEvent.collectLatest { event ->
            when (event) {
                is TransactionsContract.Events.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }
                is TransactionsContract.Events.ShareReceipt -> {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "image/png"
                        clipData = ClipData.newRawUri("receipt", event.uri)
                        putExtra(Intent.EXTRA_STREAM, event.uri)
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    }
                    context.startActivity(
                        Intent.createChooser(
                            shareIntent,
                            "Share receipt",
                        ),
                    )
                }
            }
        }
    }

    TransactionHistoryContent(
        state = state.value,
        onRetry = { viewModel.processIntent(TransactionsContract.Actions.Retry) },
        onTransactionLongPress = { transaction: TransactionListItem ->
            viewModel.processIntent(TransactionsContract.Actions.SelectTransaction(transaction))
        },
        modifier = modifier.fillMaxSize(),
    )

    state.value.selectedTransaction?.let {
        ModalBottomSheet(
            onDismissRequest = { viewModel.processIntent(TransactionsContract.Actions.DismissReceiptActions) },
        ) {
            ListItem(
                headlineContent = { Text("Share") },
                modifier = Modifier.combinedClickable(
                    onClick = { viewModel.processIntent(TransactionsContract.Actions.ShareSelectedReceipt) },
                    onLongClick = null,
                ),
            )
            ListItem(
                headlineContent = { Text("Download") },
                modifier = Modifier.combinedClickable(
                    onClick = { viewModel.processIntent(TransactionsContract.Actions.DownloadSelectedReceipt) },
                    onLongClick = null,
                ),
            )
        }
    }
}
