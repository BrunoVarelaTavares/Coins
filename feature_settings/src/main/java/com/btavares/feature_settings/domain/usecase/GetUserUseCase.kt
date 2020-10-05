package com.btavares.feature_settings.domain.usecase

import com.btavares.feature_settings.domain.model.UserDomainModel
import com.btavares.feature_settings.domain.repository.SettingsRepository
import java.io.IOException

class GetUserUseCase(private val settingsRepository: SettingsRepository ) {

    sealed class Result{
        data class Success(val data: UserDomainModel) : Result()
        data class Error(val e: Throwable) : Result()
    }

    suspend fun execute(
    ): Result = try {
        settingsRepository.getUser(
        )?.let {
            Result.Success(it)
        }
    }catch (e: IOException){
        Result.Error(e)
    }

}