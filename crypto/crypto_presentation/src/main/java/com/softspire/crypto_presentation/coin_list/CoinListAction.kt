package com.softspire.crypto_presentation.coin_list

import com.softspire.crypto_presentation.model.CoinUi

sealed interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi): CoinListAction
//    data object OnRefresh: CoinListAction
}