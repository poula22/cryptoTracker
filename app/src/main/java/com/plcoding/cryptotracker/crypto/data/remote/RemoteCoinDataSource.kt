package com.plcoding.cryptotracker.crypto.data.remote

import com.plcoding.cryptotracker.core.data.remote.constructUrl
import com.plcoding.cryptotracker.core.data.remote.safeCall
import com.plcoding.cryptotracker.core.domain.util.NetworkError
import com.plcoding.cryptotracker.core.domain.util.Result
import com.plcoding.cryptotracker.core.domain.util.map
import com.plcoding.cryptotracker.crypto.data.dto.coin.CoinListResponseDto
import com.plcoding.cryptotracker.crypto.data.dto.coin_price.CoinPriceListDto
import com.plcoding.cryptotracker.crypto.data.mapper.toCoin
import com.plcoding.cryptotracker.crypto.data.mapper.toCoinPrice
import com.plcoding.cryptotracker.crypto.domain.dataSource.CoinDataSource
import com.plcoding.cryptotracker.crypto.domain.model.Coin
import com.plcoding.cryptotracker.crypto.domain.model.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime

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

    override suspend fun getCoinHistory(
        coinId: String,
        start: ZonedDateTime,
        end: ZonedDateTime
    ): Result<List<CoinPrice>, NetworkError> {
        val startTime = start
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()

        val endTime = end
            .withZoneSameInstant(ZoneId.of("UTC"))
            .toInstant()
            .toEpochMilli()

        return safeCall<CoinPriceListDto> {
            httpClient.get(
                urlString = constructUrl("/assets/$coinId/history")
            ) {
                parameter("interval", "h6")
                parameter("start", startTime)
                parameter("end", endTime)
            }
        }.map { response ->
            response.data.map { it.toCoinPrice() }
        }
    }
}