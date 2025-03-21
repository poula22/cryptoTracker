package com.softspire.crypto_presentation.coin_list

import androidx.compose.runtime.Immutable
import com.softspire.crypto_presentation.model.CoinUi

@Immutable
data class CoinListState(
    val isLoading: Boolean = false,
    val coins: List<CoinUi> = emptyList(),
    val selectedCoin: CoinUi? = null
)
