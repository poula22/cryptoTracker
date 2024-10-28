package com.plcoding.cryptotracker.crypto.presentaion.coin_list

import androidx.compose.runtime.Immutable
import com.plcoding.cryptotracker.crypto.presentaion.model.CoinUi

@Immutable
data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<CoinUi> = emptyList(),
    val selectedCoin: CoinUi? = null
)
