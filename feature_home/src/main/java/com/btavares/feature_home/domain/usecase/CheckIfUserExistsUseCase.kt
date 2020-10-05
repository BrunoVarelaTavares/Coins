package com.btavares.feature_home.domain.usecase

import com.btavares.feature_home.domain.repository.HomeRepository

internal class CheckIfUserExistsUseCase (private val homeRepository: HomeRepository) {


    sealed class Result{
        data class Success(val data: Boolean) : Result()
        data class Error(val e: Throwable) : Result()
    }

    suspend fun execute(
    ): Result = try {
        homeRepository.checkIfUserExists(
        ).let {
            Result.Success(it)
        }
    }catch (e: Exception){
        Result.Error(e)
    }

}