package com.plcoding.cryptotracker.crypto.data.dto.coin_price

import kotlinx.serialization.Serializable

@Serializable
data class CoinPriceDto(
    val priceUsd: Double,
    val time: Long
)