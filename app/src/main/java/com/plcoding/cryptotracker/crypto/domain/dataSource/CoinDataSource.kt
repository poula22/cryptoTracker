package com.plcoding.cryptotracker.crypto.domain.dataSource

import com.plcoding.cryptotracker.core.domain.util.NetworkError
import com.plcoding.cryptotracker.core.domain.util.Result
import com.plcoding.cryptotracker.crypto.domain.model.Coin
import com.plcoding.cryptotracker.crypto.domain.model.CoinPrice
import java.time.ZonedDateTime

interface CoinDataSource {
    suspend fun getCoin(): Result<List<Coin>, NetworkError>
    suspend fun getCoinHistory(
        coinId:String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError>
}