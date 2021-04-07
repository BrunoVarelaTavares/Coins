package com.btavares.feature_portfolio.domain.usecase


import com.btavares.feature_portfolio.domain.repository.PortfolioRepository

internal class SavePortfolioCurrencyValueUseCase(private val portfolioRepository: PortfolioRepository) {

    sealed class Result{
        data class Success(val data: Boolean) : Result()
        data class Error(val e: Throwable) : Result()
    }

    suspend fun execute(
        cryptoId: String,
        nativeCurrencyValue : Double
    ): Result = try {
        portfolioRepository.savePortfolioNativeCurrencyValue(
            cryptoId,
            nativeCurrencyValue
        ).let {
            Result.Success(true)
        }
    }catch (e: Exception){
        Result.Error(e)
    }

}