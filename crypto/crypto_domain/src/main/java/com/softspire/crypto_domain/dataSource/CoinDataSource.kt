package com.softspire.crypto_domain.dataSource

import com.softspire.crypto_domain.model.Coin
import com.softspire.crypto_domain.model.CoinPrice
import com.softspire.core_domain.util.NetworkError
import java.time.ZonedDateTime
import com.softspire.core_domain.util.Result

interface CoinDataSource {
    suspend fun getCoin(): Result<List<Coin>, NetworkError>
    suspend fun getCoinHistory(
        coinId:String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError>
}