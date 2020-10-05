package com.btavares.feature_coin_detail.domain.usecase

import com.btavares.feature_coin_detail.domain.model.CryptocurrencyMarketChartDomainModel
import com.btavares.feature_coin_detail.domain.repository.CoinDetailRepository


internal class GetCryptocurrencyChartDataUseCase(private val coinDetailRepository: CoinDetailRepository){

    sealed class Result {
        data class Success(val data : CryptocurrencyMarketChartDomainModel?) : Result()
        data class Error(val e: Throwable) : Result()
    }


    suspend fun execute(
        id : String,
        currency: String?,
        days: String?
    ) : Result = try {
        coinDetailRepository.getCryptocurrencyMarketChart(
            id,
            currency,
            days
        ).let {
            Result.Success(it)
        }

    } catch (e: Exception){
        Result.Error(e)
    }
}