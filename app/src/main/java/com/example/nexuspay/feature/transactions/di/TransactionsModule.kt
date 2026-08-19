package com.example.nexuspay.feature.transactions.di

import android.content.Context
import androidx.room.Room
import androidx.work.WorkManager
import com.example.nexuspay.core.local.UserDataStore
import com.example.nexuspay.feature.transactions.data.local.TransactionsDatabase
import com.example.nexuspay.feature.transactions.data.local.dao.PendingRequestDao
import com.example.nexuspay.feature.transactions.data.local.dao.TransactionDao
import com.example.nexuspay.feature.transactions.data.remote.ITransactionsRemoteDataSource
import com.example.nexuspay.feature.transactions.data.remote.TransactionsApi
import com.example.nexuspay.feature.transactions.data.remote.TransactionsRemoteDataSource
import com.example.nexuspay.feature.transactions.data.repository.PendingRequestRepository
import com.example.nexuspay.feature.transactions.data.repository.TransactionsRepository
import com.example.nexuspay.feature.transactions.data.repository.UserRepository
import com.example.nexuspay.feature.transactions.domain.repository.IPendingRequestRepository
import com.example.nexuspay.feature.transactions.domain.repository.ITransactionsRepository
import com.example.nexuspay.feature.transactions.domain.repository.IUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TransactionsModule {
    @Provides
    @Singleton
    fun provideTransactionsDatabase(@ApplicationContext context: Context): TransactionsDatabase =
        Room.databaseBuilder(context, TransactionsDatabase::class.java, "nexuspay_transactions.db")
            .addMigrations(TransactionsDatabase.MIGRATION_1_2)
            .build()

    @Provides
    fun provideTransactionDao(database: TransactionsDatabase): TransactionDao = database.transactionDao()

    @Provides
    fun providePendingRequestDao(database: TransactionsDatabase): PendingRequestDao =
        database.pendingRequestDao()

    @Provides
    @Singleton
    fun provideTransactionsApi(retrofit: Retrofit): TransactionsApi =
        retrofit.create(TransactionsApi::class.java)

    @Provides
    @Singleton
    fun provideTransactionsRemoteDataSource(api: TransactionsApi): ITransactionsRemoteDataSource =
        TransactionsRemoteDataSource(api)

    @Provides
    @Singleton
    fun provideTransactionsRepository(
        transactionDao: TransactionDao,
        pendingRequestDao: PendingRequestDao,
        remoteDataSource: ITransactionsRemoteDataSource,
        json: Json,
    ): ITransactionsRepository = TransactionsRepository(
        transactionDao,
        pendingRequestDao,
        remoteDataSource,
        json,
    )

    @Provides
    @Singleton
    fun providePendingRequestRepository(dao: PendingRequestDao): IPendingRequestRepository =
        PendingRequestRepository(dao)

    @Provides
    @Singleton
    fun provideUserRepository(dataStore: UserDataStore): IUserRepository = UserRepository(dataStore)

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager = WorkManager.getInstance(context)
}