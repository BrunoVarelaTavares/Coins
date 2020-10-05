package com.btavares.feature_coin_detail.domain.usecase


import com.btavares.feature_coin_detail.domain.model.CryptocurrencyMarketChartDomainModel
import com.btavares.feature_coin_detail.domain.repository.CoinDetailRepository
import java.io.IOException

internal class GetCryptocurrencyMarketChartByRangeUseCase(private val coinDetailRepository: CoinDetailRepository){

    sealed class Result {
        data class Success(val data : CryptocurrencyMarketChartDomainModel?) : Result()
        data class Error(val e: Throwable) : Result()
    }

    suspend fun execute(
        id : String,
        currency: String?,
        fromTimestamp : String,
        toTimestamp : String
    ) : Result = try {
        coinDetailRepository.getCryptocurrencyMarketChartByRange(
            id,
            currency,
            fromTimestamp,
            toTimestamp
        )?.let {
            Result.Success(it)
        }?: Result.Error(RuntimeException("No Data"))
    } catch (e: IOException){
        Result.Error(e)
    }
}