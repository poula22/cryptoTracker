package com.plcoding.cryptotracker.crypto.domain.dataSource

import com.plcoding.cryptotracker.core.domain.util.NetworkError
import com.plcoding.cryptotracker.core.domain.util.Result
import com.plcoding.cryptotracker.crypto.domain.model.Coin

interface CoinDataSource {
    suspend fun getCoin(): Result<List<Coin>, NetworkError>
}