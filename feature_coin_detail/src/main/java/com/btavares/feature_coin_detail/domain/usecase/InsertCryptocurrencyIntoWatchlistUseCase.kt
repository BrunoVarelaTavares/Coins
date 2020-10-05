package com.btavares.feature_coin_detail.domain.usecase

import com.btavares.feature_coin_detail.domain.repository.CoinDetailRepository

internal class InsertCryptocurrencyIntoWatchlistUseCase(private val coinDetailRepository: CoinDetailRepository) {

    suspend fun execute(cryptocurrencyId : String) = coinDetailRepository.insertCryptocurrencyInWatchlist(cryptocurrencyId)

}