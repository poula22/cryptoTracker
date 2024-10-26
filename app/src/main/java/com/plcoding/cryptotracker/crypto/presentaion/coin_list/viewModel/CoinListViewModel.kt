package com.plcoding.cryptotracker.crypto.presentaion.coin_list.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.plcoding.cryptotracker.core.domain.util.onError
import com.plcoding.cryptotracker.core.domain.util.onSuccess
import com.plcoding.cryptotracker.crypto.domain.dataSource.CoinDataSource
import com.plcoding.cryptotracker.crypto.presentaion.coin_list.CoinListAction
import com.plcoding.cryptotracker.crypto.presentaion.coin_list.CoinListEvent
import com.plcoding.cryptotracker.crypto.presentaion.coin_list.model.CoinListState
import com.plcoding.cryptotracker.crypto.presentaion.coin_list.model.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CoinListViewModel(
    private val coinDataSource: CoinDataSource
): ViewModel() {
    private val _state = MutableStateFlow(CoinListState())
    val state = _state
        .onStart {
            loadCoins()
        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            CoinListState()
        )

    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: CoinListAction) {
        when(action) {
            is CoinListAction.OnCoinClick -> {

            }
//            CoinListAction.OnRefresh -> {
//                loadCoins()
//            }
        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            _state.update { it.copy(
                isLoading = false
            ) }
            coinDataSource
                .getCoin()
                .onSuccess { coins->
                    _state.update { it.copy(
                        isLoading = false,
                        coins = coins.map { coin -> coin.toCoinUi() }
                    ) }
                }
                .onError { error ->
                    _state.update { it.copy(
                        isLoading = false
                    ) }
                    _events.send(CoinListEvent.Error(error))
                }
        }
    }
}