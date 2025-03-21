package com.softspire.crypto_data.dto.coin

import kotlinx.serialization.Serializable

@Serializable
data class CoinListResponseDto(
    val data: List<CoinResponseDto>,
    val timestamp: Long
)