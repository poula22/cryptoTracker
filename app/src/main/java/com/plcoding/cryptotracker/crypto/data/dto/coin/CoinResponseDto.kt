package com.plcoding.cryptotracker.crypto.data.dto.coin

import kotlinx.serialization.Serializable

@Serializable
data class CoinResponseDto(
    val id: String,
    val rank: Int,
    val name: String,
    val symbol: String,
    val marketCapUsd: Double,
    val priceUsd: Double,
    val changePercent24Hr: Double
)