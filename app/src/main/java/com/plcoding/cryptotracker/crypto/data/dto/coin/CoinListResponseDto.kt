package com.plcoding.cryptotracker.crypto.data.dto.coin

import kotlinx.serialization.Serializable

@Serializable
data class CoinListResponseDto(
    val data: List<CoinResponseDto>,
    val timestamp: Long
)