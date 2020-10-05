package com.btavares.feature_coin_detail.domain.usecase

import com.btavares.feature_coin_detail.domain.repository.CoinDetailRepository


internal class DeleteCryptocurrencyFromWatchlistUseCase(private val coinDetailRepository: CoinDetailRepository) {

    sealed class Result {
        data class Success(val data : Boolean) : Result()
        data class Error(val e: Throwable) : Result()
    }


    suspend fun execute(
        cryptocurrencyId : String
    ): Result = try {
        coinDetailRepository.deleteCryptocurrencyInWatchlist(
            cryptocurrencyId
        ).let {
            Result.Success(true)
        }
    } catch (e: Exception){
        Result.Error(e)
    }
}