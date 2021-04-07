package com.btavares.feature_home.domain.usecase

import com.btavares.feature_home.domain.repository.HomeRepository
import java.io.IOException

internal class GetUserPortfolioBalanceUseCase(private val homeRepository: HomeRepository) {

    sealed class Result{
        data class Success(val data: Double) : Result()
        data class Error(val e: Throwable) : Result()
    }

    suspend fun execute(
    ): Result = try {
        homeRepository.getUserBalance(
        ).let {
            Result.Success(it)
        }
    }catch (e: IOException){
        Result.Error(e)
    }

}