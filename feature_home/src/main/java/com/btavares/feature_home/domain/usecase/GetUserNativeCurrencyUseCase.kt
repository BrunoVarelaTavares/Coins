package com.btavares.feature_home.domain.usecase

import com.btavares.feature_home.domain.model.NativeCurrencyDomainModel
import com.btavares.feature_home.domain.repository.HomeRepository

internal class GetUserNativeCurrencyUseCase(private val homeRepository: HomeRepository) {

    sealed class Result{
        data class Success(val data: NativeCurrencyDomainModel) : Result()
        data class Error(val e: Throwable) : Result()
    }

    suspend fun execute(
    ): Result = try {
        homeRepository.getNativeCurrency(
        ).let {
            Result.Success(it)
        }
    }catch (e: Exception){
        Result.Error(e)
    }

}