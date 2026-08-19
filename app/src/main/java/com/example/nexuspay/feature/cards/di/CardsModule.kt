package com.example.nexuspay.feature.cards.di

import com.example.nexuspay.feature.cards.data.local.dao.CardDao
import com.example.nexuspay.feature.cards.data.repository.CardRepository
import com.example.nexuspay.feature.cards.domain.repository.CardsRepository
import com.example.nexuspay.feature.transactions.data.local.TransactionsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CardsModule {
    @Provides
    fun provideCardDao(database: TransactionsDatabase): CardDao = database.cardDao()

    @Provides
    @Singleton
    fun provideCardsRepository(cardDao: CardDao): CardsRepository = CardRepository(cardDao)
}