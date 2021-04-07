package com.btavares.feature_coin_detail.domain.usecase

import com.btavares.feature_coin_detail.domain.model.CryptocurrencyMarketDomainModel
import com.btavares.feature_coin_detail.domain.repository.CoinDetailRepository
import java.io.IOException

internal class GetCryptocurrencyMarketDataUseCase(private val coinDetailRepository: CoinDetailRepository) {

    sealed class Result {
        data class Success(val data: MutableList<CryptocurrencyMarketDomainModel>) : Result()
        data class Error(val e: Throwable) : Result()
    }

    suspend fun execute(
        currency: String
    ): Result = try {
        coinDetailRepository.getCryptocurrencyMarketData(
            currency
        ).let {
            Result.Success(it)
        }


    } catch (e: IOException) {
        Result.Error(e)
    }

}