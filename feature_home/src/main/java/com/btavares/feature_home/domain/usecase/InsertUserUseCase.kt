package com.btavares.feature_home.domain.usecase

import com.btavares.feature_home.domain.repository.HomeRepository
import java.io.IOException

internal class InsertUserUseCase(private val homeRepository: HomeRepository) {

    sealed class Result{
        data class Success(val data: Boolean) : Result()
        data class Error(val e: Throwable) : Result()
    }

    suspend fun execute(
        userName : String,
        userEmail: String
    ): Result = try {
        homeRepository.insertUser(
            userName,
            userEmail
        ).let {
            Result.Success(true)
        }
    }catch (e: IOException){
        Result.Error(e)
    }

}