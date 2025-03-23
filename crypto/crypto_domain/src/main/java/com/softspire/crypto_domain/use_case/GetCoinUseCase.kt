package com.softspire.crypto_domain.use_case

import com.softspire.core_domain.useCase.UseCase
import com.softspire.core_domain.util.DomainError
import com.softspire.core_domain.util.ResponseModel
import com.softspire.crypto_domain.model.Coin
import com.softspire.crypto_domain.repository.CoinRepository
import com.softspire.core_domain.useCase.UseCase.None

class GetCoinUseCase(private val repository: CoinRepository) :
    UseCase<List<Coin>, None>() {

    override suspend fun run(params: None): ResponseModel<List<Coin>, DomainError> {
        return repository.getCoin()
    }
}