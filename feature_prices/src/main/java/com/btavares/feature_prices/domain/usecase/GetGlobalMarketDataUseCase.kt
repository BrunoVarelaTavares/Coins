package com.btavares.feature_prices.domain.usecase

import com.btavares.feature_prices.domain.model.GlobalMarketDataDomainModel
import com.btavares.feature_prices.domain.repository.PricesRepository
import java.io.IOException

internal class GetGlobalMarketDataUseCase( private val pricesRepository: PricesRepository) {

    sealed class Result {
        data class Success(val data: GlobalMarketDataDomainModel) : Result()
        data class Error(val e: Throwable) : Result()
    }

    suspend fun execute(
    ): Result = try {
        pricesRepository.getGlobalPrices(
        )?.let {
            Result.Success(it)
        }


    } catch (e: IOException) {
        Result.Error(e)
    }


}