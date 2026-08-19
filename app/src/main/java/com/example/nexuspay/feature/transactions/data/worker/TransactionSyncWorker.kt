package com.example.nexuspay.feature.transactions.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.nexuspay.feature.transactions.data.model.SendTransactionPayload
import com.example.nexuspay.feature.transactions.data.remote.ITransactionsRemoteDataSource
import com.example.nexuspay.feature.transactions.domain.model.PendingRequest
import com.example.nexuspay.feature.transactions.domain.model.PendingRequestType
import com.example.nexuspay.feature.transactions.domain.repository.IPendingRequestRepository
import com.example.nexuspay.feature.transactions.domain.repository.ITransactionsRepository
import com.example.nexuspay.feature.transactions.domain.repository.IUserRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.serialization.json.Json

@HiltWorker
class TransactionSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters,
    private val pendingRequestRepository: IPendingRequestRepository,
    private val transactionsRepository: ITransactionsRepository,
    private val remoteDataSource: ITransactionsRemoteDataSource,
    private val userRepository: IUserRepository,
    private val json: Json,
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val localId = inputData.getString(KEY_LOCAL_ID) ?: return Result.failure()
        val requestType = inputData.getString(KEY_TYPE) ?: return Result.failure()
        val payload = inputData.getString(KEY_PAYLOAD) ?: return Result.failure()

        return try {
            when (PendingRequestType.valueOf(requestType)) {
                PendingRequestType.SEND_TRANSACTION -> {
                    remoteDataSource.sendTransaction(
                        payload = json.decodeFromString<SendTransactionPayload>(payload),
                        idempotencyKey = localId,
                    )
                    pendingRequestRepository.delete(localId)
                    transactionsRepository.syncFromRemote(userRepository.getIdentifier())
                }
            }
            Result.success()
        } catch (exception: Exception) {
            pendingRequestRepository.delete(localId)
            Result.failure()
        }
    }

    companion object {
        const val KEY_LOCAL_ID = "local_id"
        const val KEY_TYPE = "type"
        const val KEY_PAYLOAD = "payload"
        const val TAG = "transaction_sync"
        fun buildRequest(request: PendingRequest): OneTimeWorkRequest =
            OneTimeWorkRequestBuilder<TransactionSyncWorker>()
                .setInputData(
                    workDataOf(
                        KEY_LOCAL_ID to request.localId,
                        KEY_TYPE to request.type.name,
                        KEY_PAYLOAD to request.payload,
                    ),
                )
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .build(),
                )
                .addTag(TAG)
                .addTag(request.localId)
                .build()
    }
}