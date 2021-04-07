package com.btavares.feature_home.domain.usecase

import com.btavares.feature_home.domain.model.CryptocurrencyMarketDomainModel
import com.btavares.feature_home.domain.repository.HomeRepository
import java.io.IOException

internal class GetAllMarketDataUseCase(private val homeRepository: HomeRepository) {


    sealed class Result{
        data class Success(val data: List<CryptocurrencyMarketDomainModel>) : Result()
        data class Error(val e: Throwable) : Result()
    }

    suspend fun execute(
        currency: String?
    ): Result = try {
        homeRepository.getAllMarketDataAsync(
            currency
            ).let {

            Result.Success(it)
        }
    } catch (e: IOException){
        Result.Error(e)
    }





}