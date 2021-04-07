package com.btavares.feature_home.domain.usecase

import com.btavares.feature_home.domain.model.NewsDomainModel
import com.btavares.feature_home.domain.repository.HomeRepository
import java.io.IOException

internal class GetLatestNewsUseCase(
    private val homeRepository: HomeRepository
) {

    sealed class Result{
        data class Success(val data: List<NewsDomainModel>) : Result()
        data class Error(val e: Throwable) : Result()
    }

    suspend fun execute(
    ): Result = try {
        homeRepository.getCryptocurrenciesLatestNewsAsync(
        )?.let {
            Result.Success(it)
        }?: Result.Error(RuntimeException("No data"))
    }catch (e: Exception){
        Result.Error(e)
    }

}