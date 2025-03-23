package com.softspire.crypto_domain.repository

import com.softspire.core_domain.util.DomainError
import com.softspire.core_domain.util.ResponseModel
import com.softspire.crypto_domain.model.Coin
import com.softspire.crypto_domain.model.CoinPrice
import java.time.ZonedDateTime

interface CoinRepository {
    suspend fun getCoin(): ResponseModel<List<Coin>,DomainError>

    suspend fun getCoinHistory(
        coinId:String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): ResponseModel<List<CoinPrice>, DomainError>
}