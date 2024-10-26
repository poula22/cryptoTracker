package com.plcoding.cryptotracker.crypto.presentaion.coin_list

import com.plcoding.cryptotracker.core.domain.util.NetworkError

interface CoinListEvent {
    data class Error(val error: NetworkError): CoinListEvent
}