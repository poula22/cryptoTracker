package com.plcoding.cryptotracker.crypto.data.mapper

import com.plcoding.cryptotracker.crypto.data.dto.coin_price.CoinPriceDto
import com.plcoding.cryptotracker.crypto.domain.model.CoinPrice
import java.time.Instant
import java.time.ZoneId

fun CoinPriceDto.toCoinPrice(): CoinPrice {
    return CoinPrice(
        priceUsd = priceUsd,
        dateTime = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault())
    )
}