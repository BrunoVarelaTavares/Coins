package com.btavares.feature_prices.domain.usecase


import com.btavares.feature_prices.domain.repository.PricesRepository

internal class GetUserNativeCurrencyUseCase(private val pricesRepository: PricesRepository) {


    suspend fun execute() = pricesRepository.getNativeCurrency()

}