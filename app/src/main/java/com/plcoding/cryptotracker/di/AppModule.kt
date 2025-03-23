package com.plcoding.cryptotracker.di

import com.softspire.crypto_data.remote.RemoteCoinDataSource
import com.softspire.crypto_domain.repository.CoinRepository
import com.softspire.crypto_presentation.viewModel.CoinListViewModel
import com.softspire.core_data.remote.HttpClientFactory
import com.softspire.crypto_domain.use_case.GetCoinHistoryUseCase
import com.softspire.crypto_domain.use_case.GetCoinUseCase
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::RemoteCoinDataSource).bind<CoinRepository>()
    singleOf(::GetCoinUseCase).bind<GetCoinUseCase>()
    singleOf(::GetCoinHistoryUseCase).bind<GetCoinHistoryUseCase>()
    viewModelOf(::CoinListViewModel)
}