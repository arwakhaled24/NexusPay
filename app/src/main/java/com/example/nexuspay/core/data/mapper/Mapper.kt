package com.example.nexuspay.core.data.mapper

abstract class Mapper<Dto, Domain> {

    abstract fun dtoToDomain(model: Dto): Domain

    fun dtoToDomain(list: List<Dto>?): List<Domain> =
        (list ?: emptyList()).map(::dtoToDomain)
}
