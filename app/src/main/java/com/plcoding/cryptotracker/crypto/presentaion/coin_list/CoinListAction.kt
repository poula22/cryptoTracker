package com.plcoding.cryptotracker.crypto.presentaion.coin_list

import com.plcoding.cryptotracker.crypto.presentaion.coin_list.model.CoinUi

sealed interface CoinListAction {
    data class OnCoinClick(val coinUi: CoinUi): CoinListAction
//    data object OnRefresh: CoinListAction
}