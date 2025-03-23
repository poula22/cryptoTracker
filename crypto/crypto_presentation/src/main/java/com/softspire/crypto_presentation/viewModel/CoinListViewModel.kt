package com.softspire.crypto_presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.softspire.core_domain.useCase.UseCase
import com.softspire.core_domain.util.DomainError
import com.softspire.core_domain.util.NetworkError
import com.softspire.core_domain.util.ResponseModel
import com.softspire.core_presentation.util.toViewModelState
import com.softspire.crypto_domain.use_case.GetCoinHistoryUseCase
import com.softspire.crypto_domain.use_case.GetCoinUseCase
import com.softspire.crypto_presentation.coin_detail.model.DataPoint
import com.softspire.crypto_presentation.coin_list.CoinListAction
import com.softspire.crypto_presentation.coin_list.CoinListEvent
import com.softspire.crypto_presentation.coin_list.CoinListState
import com.softspire.crypto_presentation.model.CoinUi
import com.softspire.crypto_presentation.model.toCoinUi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

class CoinListViewModel(
    private val getCoinUseCase: GetCoinUseCase,
    private val getCoinHistoryUseCase: GetCoinHistoryUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(CoinListState())
    val state = _state.toViewModelState(viewModelScope, ::loadCoins)

    private val _events = Channel<CoinListEvent>()
    val events = _events.receiveAsFlow()

    fun onAction(action: CoinListAction) {
        when (action) {
            is CoinListAction.OnCoinClick -> {
                selectCoin(action.coinUi)
            }
//            CoinListAction.OnRefresh -> {
//                loadCoins()
//            }
        }
    }

    private fun selectCoin(coinUi: CoinUi) {
        _state.update { it.copy(selectedCoin = coinUi) }

        viewModelScope.launch {
            getCoinHistoryUseCase.invoke(
                GetCoinHistoryUseCase.Params(
                    coinId = coinUi.id,
                    start = ZonedDateTime.now().minusDays(5),
                    end = ZonedDateTime.now()
                )
            ).collect { response ->
                response.handleResponse { history ->
                    val dataPoints = history
                        .sortedBy { it.dateTime }
                        .map {
                            DataPoint(
                                x = it.dateTime.hour.toFloat(),
                                y = it.priceUsd.toFloat(),
                                xLabel = DateTimeFormatter
                                    .ofPattern("ha\nM/d")
                                    .format(it.dateTime)
                            )
                        }

                    _state.update {
                        it.copy(
                            selectedCoin = it.selectedCoin?.copy(
                                coinPriceHistory = dataPoints
                            )
                        )
                    }
                }
            }
        }
    }

    private fun loadCoins() {
        viewModelScope.launch {
            getCoinUseCase
                .invoke(UseCase.None)
                .collect { response ->
                    response.handleResponse { coins ->
                        _state.update {
                            it.copy(
                                isLoading = false,
                                coins = coins.map { coin -> coin.toCoinUi() }
                            )
                        }
                    }
                }
        }
    }

    private suspend inline fun <T> ResponseModel<T, DomainError>.handleResponse(
        onSuccess: (T) -> Unit,
    ) {
        when (this) {
            ResponseModel.Loading -> _state.update {
                it.copy(isLoading = true)
            }

            is ResponseModel.Error<DomainError> -> {
                _state.update {
                    it.copy(isLoading = false)
                }

                when (this.error) {
                    is NetworkError -> _events.send(CoinListEvent.Error(this.error as NetworkError))
                }
            }

            is ResponseModel.Success<T> -> onSuccess(this.data)
        }
    }
}