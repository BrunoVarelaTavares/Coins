package com.btavares.feature_home.domain.usecase

import com.btavares.feature_home.domain.model.NewsDomainModel
import com.btavares.feature_home.domain.repository.HomeRepository

internal class GetNewsUseCase(private val homeRepository: HomeRepository) {

    sealed class Result{
        data class Success(val data: List<NewsDomainModel>) : Result()
        data class Error(val e: Throwable) : Result()
    }

    suspend fun execute(
    ): Result = try {
        homeRepository.getCryptocurrenciesTopNewsAsync(
        ).let {
            Result.Success(it)
        }
    }catch (e: Exception){
        Result.Error(e)
    }

}