package com.softspire.crypto_domain.use_case

import com.softspire.core_domain.useCase.UseCase
import com.softspire.core_domain.util.DomainError
import com.softspire.core_domain.util.ResponseModel
import com.softspire.crypto_domain.model.CoinPrice
import com.softspire.crypto_domain.repository.CoinRepository
import java.time.ZonedDateTime
import com.softspire.crypto_domain.use_case.GetCoinHistoryUseCase.Params

class GetCoinHistoryUseCase(private val coinRepository: CoinRepository) :
    UseCase<List<CoinPrice>, Params>() {

    override suspend fun run(params: Params): ResponseModel<List<CoinPrice>, DomainError> {
        return coinRepository.getCoinHistory(
            coinId = params.coinId,
            start = params.start,
            end = params.end
        )
    }

    data class Params(
        val coinId: String,
        val start: ZonedDateTime,
        val end: ZonedDateTime
    )
}