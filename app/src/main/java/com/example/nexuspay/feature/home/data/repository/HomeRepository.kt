package com.example.nexuspay.feature.home.data.repository

import com.example.nexuspay.feature.home.data.mapper.TransactionMapper
import com.example.nexuspay.feature.home.data.mapper.UserMapper
import com.example.nexuspay.feature.home.data.remote.IHomeRemoteDataSource
import com.example.nexuspay.feature.home.domain.model.HomeData
import com.example.nexuspay.feature.home.domain.repository.IHomeRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val remoteDataSource: IHomeRemoteDataSource,
) : IHomeRepository {

    override suspend fun getHomeData(identifier: String): HomeData = coroutineScope {
        val userDeferred = async { remoteDataSource.getUser(identifier) }
        val transactionsDeferred = async { remoteDataSource.getTransactions(identifier) }

        HomeData(
            user = UserMapper.dtoToDomain(userDeferred.await()),
            recentTransactions = TransactionMapper.dtoToDomain(transactionsDeferred.await())
                .sortedByDescending { "${it.date}T${it.time}" }
                .take(5),
        )
    }
}
