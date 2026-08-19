package com.example.nexuspay.feature.transactions.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nexuspay.feature.transactions.data.local.entity.PendingRequestEntity
import com.example.nexuspay.feature.transactions.domain.model.PendingRequestType
import kotlinx.coroutines.flow.Flow

@Dao
interface PendingRequestDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: PendingRequestEntity)

    @Query("DELETE FROM pending_requests WHERE localId = :localId")
    suspend fun delete(localId: String)

    @Query("SELECT * FROM pending_requests WHERE type = :type")
    fun observeByType(type: String): Flow<List<PendingRequestEntity>>
}