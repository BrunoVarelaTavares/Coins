package com.btavares.feature_coin_detail.domain.usecase

import com.btavares.feature_coin_detail.domain.repository.CoinDetailRepository
import java.io.IOException

internal class CheckIfCryptocurrencyExistsInWatchlistUseCase (private val coinDetailRepository: CoinDetailRepository) {

    sealed class Result{
        data class Success(val data: Boolean) : Result()
        data class Error(val e: Throwable) : Result()
    }

    suspend fun execute(
        cryptocurrencyId: String
    ): Result = try {
        coinDetailRepository.checkCryptocurrencyExistsInWatchlist(
            cryptocurrencyId
        )?.let {
            Result.Success(it)
        }
    }catch (e: IOException){
        Result.Error(e)
    }

}