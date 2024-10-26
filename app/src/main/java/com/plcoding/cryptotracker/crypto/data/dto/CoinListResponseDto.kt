package com.plcoding.cryptotracker.crypto.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CoinListResponseDto(
    val data: List<CoinResponseDto>,
    val timestamp: Long
)