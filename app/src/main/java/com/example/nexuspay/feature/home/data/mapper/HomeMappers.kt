package com.example.nexuspay.feature.home.data.mapper

import com.example.nexuspay.core.data.mapper.Mapper
import com.example.nexuspay.feature.home.data.model.TransactionDto
import com.example.nexuspay.feature.home.data.model.UserDto
import com.example.nexuspay.feature.home.domain.model.Transaction
import com.example.nexuspay.feature.home.domain.model.TransactionState
import com.example.nexuspay.feature.home.domain.model.TransactionType
import com.example.nexuspay.feature.home.domain.model.User
import com.example.nexuspay.feature.home.domain.model.Money

object UserMapper : Mapper<UserDto, User>() {
    override fun dtoToDomain(model: UserDto): User = User(
        id = model.id,
        name = model.name,
        identifier = model.identifier,
        balance = Money(
            minorUnits = model.balance,
            currency = model.currency,
        ),
        avatarUrl = model.avatar,
    )
}

object TransactionMapper : Mapper<TransactionDto, Transaction>() {
    override fun dtoToDomain(model: TransactionDto): Transaction = Transaction(
        id = model.id,
        amount = Money(
            minorUnits = model.amount,
            currency = model.currency,
        ),
        type = enumValueOrUnknown(model.type, TransactionType.UNKNOWN),
        state = enumValueOrUnknown(model.state, TransactionState.UNKNOWN),
        description = model.description,
        date = model.date,
        time = model.time,
    )

    private inline fun <reified T : Enum<T>> enumValueOrUnknown(value: String, fallback: T): T =
        enumValues<T>().firstOrNull { it.name == value } ?: fallback
}
