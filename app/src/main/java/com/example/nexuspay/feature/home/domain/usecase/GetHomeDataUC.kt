package com.example.nexuspay.feature.home.domain.usecase

import com.example.nexuspay.core.interactor.BaseUseCase
import com.example.nexuspay.feature.home.domain.model.HomeData
import com.example.nexuspay.feature.home.domain.repository.IHomeRepository
import javax.inject.Inject

class GetHomeDataUC @Inject constructor(
    private val repository: IHomeRepository,
) : BaseUseCase<HomeData, String>() {

    override suspend fun execute(params: String?): HomeData {
        requireNotNull(params) { "A user identifier is required." }
        return repository.getHomeData(params)
    }
}
