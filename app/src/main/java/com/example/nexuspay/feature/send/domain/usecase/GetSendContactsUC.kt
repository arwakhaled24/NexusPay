package com.example.nexuspay.feature.send.domain.usecase

import com.example.nexuspay.core.interactor.BaseUseCase
import com.example.nexuspay.feature.send.domain.model.SendContact
import com.example.nexuspay.feature.send.domain.repository.ISendRepository
import javax.inject.Inject

class GetSendContactsUC @Inject constructor(
    private val repository: ISendRepository,
) : BaseUseCase<List<SendContact>, Unit>() {
    override suspend fun execute(params: Unit?): List<SendContact> = repository.getContacts()
}