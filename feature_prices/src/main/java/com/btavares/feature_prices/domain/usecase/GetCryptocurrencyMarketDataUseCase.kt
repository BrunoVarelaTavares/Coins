package com.btavares.feature_prices.domain.usecase

import com.btavares.feature_prices.domain.model.CryptocurrencyMarketDomainModel
import com.btavares.feature_prices.domain.model.GlobalMarketDataDomainModel
import com.btavares.feature_prices.domain.repository.PricesRepository
import java.io.IOException

internal class GetCryptocurrencyMarketDataUseCase(private val pricesRepository: PricesRepository) {

    sealed class Result {
        data class Success(val data: MutableList<CryptocurrencyMarketDomainModel>) : Result()
        data class Error(val e: Throwable) : Result()
    }

    suspend fun execute(
        currency: String
    ): Result = try {
        pricesRepository.getCryptocurrencyMarketData(
            currency
        )?.let {
            Result.Success(it)
        }


    } catch (e: IOException) {
        Result.Error(e)
    }

}