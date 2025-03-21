package com.plcoding.cryptotracker.core.presentation.di

import com.softspire.crypto_data.remote.RemoteCoinDataSource
import com.softspire.crypto_domain.dataSource.CoinDataSource
import com.softspire.crypto_presentation.viewModel.CoinListViewModel
import com.softspire.core_data.remote.HttpClientFactory
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val appModule = module {
    single { HttpClientFactory.create(CIO.create()) }
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()
    viewModelOf(::CoinListViewModel)
}