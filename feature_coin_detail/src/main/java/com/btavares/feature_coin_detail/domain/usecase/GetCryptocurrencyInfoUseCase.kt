package com.btavares.feature_coin_detail.domain.usecase


import com.btavares.feature_coin_detail.domain.model.CryptocurrencyDomainModel
import com.btavares.feature_coin_detail.domain.repository.CoinDetailRepository
import java.io.IOException

internal class GetCryptocurrencyInfoUseCase( private val coinDetailRepository: CoinDetailRepository) {

    sealed class Result{
        data class Success(val data: CryptocurrencyDomainModel) : Result()
        data class Error(val e: Throwable) : Result()
    }

    suspend fun execute(
        id : String
    ) : Result = try {
        coinDetailRepository.getCryptocurrencyInfoAsync(
            id
        )?.let {
            Result.Success(it)
        }?: Result.Error(RuntimeException("No Data"))
    }catch (e: IOException){
        Result.Error(e)
    }



}