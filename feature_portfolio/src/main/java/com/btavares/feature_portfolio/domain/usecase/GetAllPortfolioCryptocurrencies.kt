package com.btavares.feature_portfolio.domain.usecase

import com.btavares.feature_portfolio.domain.model.PortfolioCryptocurrencyDomainModel
import com.btavares.feature_portfolio.domain.repository.PortfolioRepository
import java.io.IOException

internal class GetAllPortfolioCryptocurrencies( private val repository: PortfolioRepository) {

    sealed class Result{
        data class Success(val data: List<PortfolioCryptocurrencyDomainModel>) : Result()
        data class Error(val e: Throwable) : Result()
    }

    suspend fun execute(
    ) : Result = try {
        repository.getAllCryptocurrencies(
        ).let {
            Result.Success(it)
        }
    }catch (e: IOException){
        Result.Error(e)
    }



}