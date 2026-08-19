package com.example.nexuspay.feature.transactions.data.local

import androidx.room.Database
import androidx.room.migration.Migration
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.nexuspay.feature.cards.data.local.dao.CardDao
import com.example.nexuspay.feature.cards.data.local.entity.CardEntity
import com.example.nexuspay.feature.transactions.data.local.dao.PendingRequestDao
import com.example.nexuspay.feature.transactions.data.local.dao.TransactionDao
import com.example.nexuspay.feature.transactions.data.local.entity.PendingRequestEntity
import com.example.nexuspay.feature.transactions.data.local.entity.TransactionEntity

@Database(
    entities = [TransactionEntity::class, PendingRequestEntity::class, CardEntity::class],
    version = 2,
    exportSchema = false,
)
abstract class TransactionsDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun pendingRequestDao(): PendingRequestDao
    abstract fun cardDao(): CardDao

    companion object {
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    "CREATE TABLE IF NOT EXISTS `user_cards` (`cardNumber` TEXT NOT NULL, " +
                        "`cardholderName` TEXT NOT NULL, `expiryDate` TEXT NOT NULL, " +
                        "`network` TEXT NOT NULL, `backgroundImageUrl` TEXT, " +
                        "PRIMARY KEY(`cardNumber`))",
                )
            }
        }
    }
}