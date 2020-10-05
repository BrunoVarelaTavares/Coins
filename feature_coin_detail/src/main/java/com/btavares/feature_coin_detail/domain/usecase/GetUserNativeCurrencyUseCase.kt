package com.btavares.feature_coin_detail.domain.usecase

import com.btavares.feature_coin_detail.domain.model.NativeCurrencyDomainModel
import com.btavares.feature_coin_detail.domain.model.NewsDomainModel
import com.btavares.feature_coin_detail.domain.repository.CoinDetailRepository


internal class GetUserNativeCurrencyUseCase(private val coinDetailRepository: CoinDetailRepository) {

    sealed class Result{
        data class Success(val data: NativeCurrencyDomainModel) : Result()
        data class Error(val e: Throwable) : Result()
    }
    suspend fun execute(
    ): Result = try {
        coinDetailRepository.getNativeCurrency().let {
            Result.Success(it)
        }
    }catch (e: Exception){
        Result.Error(e)
    }

}