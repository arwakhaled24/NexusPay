package com.example.nexuspay.feature.home.data.repository

import com.example.nexuspay.core.data.models.Transaction.Money
import com.example.nexuspay.core.local.CachedUser
import com.example.nexuspay.core.local.UserDataStore

import com.example.nexuspay.feature.home.data.mapper.UserMapper
import com.example.nexuspay.feature.home.data.remote.IHomeRemoteDataSource
import com.example.nexuspay.feature.home.domain.model.HomeData
import com.example.nexuspay.feature.home.domain.model.User
import com.example.nexuspay.feature.home.domain.repository.IHomeRepository
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val remoteDataSource: IHomeRemoteDataSource,
    private val userDataStore: UserDataStore,
) : IHomeRepository {

    override suspend fun getHomeData(identifier: String): HomeData = try {
        val user = UserMapper.dtoToDomain(remoteDataSource.getUser(identifier))
        userDataStore.saveCachedUser(user.toCachedUser())
        HomeData(user = user, recentTransactions = emptyList())
    } catch (exception: Exception) {
        val cachedUser = requireNotNull(userDataStore.getCachedUser()) {
            "No cached user profile is available."
        }
        HomeData(user = cachedUser.toDomain(), recentTransactions = emptyList())
    }

    private fun User.toCachedUser(): CachedUser = CachedUser(
        id = id,
        name = name,
        identifier = identifier,
        balance = balance.minorUnits,
        currency = balance.currency,
        avatarUrl = avatarUrl,
    )

    private fun CachedUser.toDomain(): User = User(
        id = id,
        name = name,
        identifier = identifier,
        balance = Money(minorUnits = balance, currency = currency),
        avatarUrl = avatarUrl,
    )
}
