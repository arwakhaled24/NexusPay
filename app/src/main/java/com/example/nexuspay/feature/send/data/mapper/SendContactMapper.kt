package com.example.nexuspay.feature.send.data.mapper

import com.example.nexuspay.core.data.mapper.Mapper
import com.example.nexuspay.feature.send.data.model.SendContactDto
import com.example.nexuspay.feature.send.domain.model.SendContact

object SendContactMapper : Mapper<SendContactDto, SendContact>() {
    override fun dtoToDomain(model: SendContactDto): SendContact = SendContact(
        id = model.id,
        identifier = model.identifier,
        name = model.name,
        avatarUrl = model.avatar,
    )
}