package com.example.nexuspay.feature.cards.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.nexuspay.feature.cards.data.local.entity.CardEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDao {
    @Query("SELECT * FROM user_cards ORDER BY cardholderName")
    suspend fun getAll(): List<CardEntity>

    @Query("SELECT * FROM user_cards ORDER BY cardholderName")
    fun observeAll(): Flow<List<CardEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(card: CardEntity)
}