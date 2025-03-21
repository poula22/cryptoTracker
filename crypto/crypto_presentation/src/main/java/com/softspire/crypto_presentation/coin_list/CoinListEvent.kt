package com.softspire.crypto_presentation.coin_list

import com.softspire.core_domain.util.NetworkError

interface CoinListEvent {
    data class Error(val error: NetworkError): CoinListEvent
}