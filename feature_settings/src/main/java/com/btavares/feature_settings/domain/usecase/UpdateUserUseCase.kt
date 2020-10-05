package com.btavares.feature_settings.domain.usecase

import com.btavares.feature_settings.domain.model.UserDomainModel
import com.btavares.feature_settings.domain.repository.SettingsRepository
import java.io.IOException

class UpdateUserUseCase(private val settingsRepository: SettingsRepository) {

    sealed class Result{
        data class Success(val data: Boolean) : Result()
        data class Error(val e: Throwable) : Result()
    }

    suspend fun execute(
        userName : String,
        userEmail: String
    ): Result = try {
        settingsRepository.updateUser(
            userName,
            userEmail
        )?.let {
            Result.Success(true)
        }
    }catch (e: IOException){
        Result.Error(e)
    }

}