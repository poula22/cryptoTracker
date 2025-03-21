package com.softspire.crypto_data.mapper

import com.softspire.crypto_data.dto.coin.CoinResponseDto
import com.softspire.crypto_domain.model.Coin

fun CoinResponseDto.toCoin(): Coin {
    return Coin(
        id = id,
        rank = rank,
        name = name,
        symbol = symbol,
        marketCapUsd = marketCapUsd,
        priceUsd = priceUsd,
        changePercent24Hr = changePercent24Hr?: 0.0
    )
}