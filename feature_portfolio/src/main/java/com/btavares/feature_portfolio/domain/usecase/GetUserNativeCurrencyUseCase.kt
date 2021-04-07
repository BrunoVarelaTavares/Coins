package com.btavares.feature_portfolio.domain.usecase

import com.btavares.feature_portfolio.domain.model.NativeCurrencyDomainModel
import com.btavares.feature_portfolio.domain.repository.PortfolioRepository

internal class GetUserNativeCurrencyUseCase(private val portfolioRepository: PortfolioRepository) {

    sealed class Result{
        data class Success(val data: NativeCurrencyDomainModel) : Result()
        data class Error(val e: Throwable) : Result()
    }

    suspend fun execute(
    ): Result = try {
        portfolioRepository.getNativeCurrency(
        ).let {
            Result.Success(it)
        }
    }catch (e: Exception){
        Result.Error(e)
    }

}