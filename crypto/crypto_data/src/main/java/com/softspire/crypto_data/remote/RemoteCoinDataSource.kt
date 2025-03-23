package com.softspire.crypto_data.remote

import com.softspire.crypto_data.dto.coin.CoinListResponseDto
import com.plcoding.cryptotracker.crypto.data.dto.coin_price.CoinPriceListDto
import com.softspire.core_data.remote.constructUrl
import com.softspire.core_data.remote.safeCall
import com.softspire.core_domain.util.NetworkError
import com.softspire.crypto_data.mapper.toCoin
import com.softspire.crypto_data.mapper.toCoinPrice
import com.softspire.crypto_domain.repository.CoinRepository
import com.softspire.crypto_domain.model.Coin
import com.softspire.crypto_domain.model.CoinPrice
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import java.time.ZoneId
import java.time.ZonedDateTime
import com.softspire.core_domain.util.ResponseModel
import com.softspire.core_domain.util.map

class RemoteCoinDataSource(
    private val httpClient: HttpClient
) : CoinRepository {
    override suspend fun getCoin(): ResponseModel<List<Coin>, NetworkError> {
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
    ): ResponseModel<List<CoinPrice>, NetworkError> {
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