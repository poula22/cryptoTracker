package com.plcoding.cryptotracker.crypto.data.remote

import com.plcoding.cryptotracker.core.data.remote.constructUrl
import com.plcoding.cryptotracker.core.data.remote.safeCall
import com.plcoding.cryptotracker.core.domain.util.NetworkError
import com.plcoding.cryptotracker.core.domain.util.Result
import com.plcoding.cryptotracker.core.domain.util.map
import com.plcoding.cryptotracker.crypto.data.dto.CoinListResponseDto
import com.plcoding.cryptotracker.crypto.data.mapper.toCoin
import com.plcoding.cryptotracker.crypto.domain.dataSource.CoinDataSource
import com.plcoding.cryptotracker.crypto.domain.model.Coin
import io.ktor.client.HttpClient
import io.ktor.client.request.get

class RemoteCoinDataSource(
    private val httpClient: HttpClient
) : CoinDataSource {
    override suspend fun getCoin(): Result<List<Coin>, NetworkError> {
        return safeCall<CoinListResponseDto> {
            httpClient.get(
                urlString = constructUrl("/assets"),
            )
        }.map { response ->
            response.data.map { it.toCoin() }
        }
    }
}