package com.btavares.feature_settings.domain.usecase

import com.btavares.feature_settings.domain.model.NativeCurrencyDomainModel
import com.btavares.feature_settings.domain.repository.SettingsRepository
import java.io.IOException

internal class GetCurrentNativeCurrencyUseCase(private val settingsRepository: SettingsRepository ) {

    sealed class Result{
        data class Success(val data: NativeCurrencyDomainModel) : Result()
        data class Error(val e: Throwable) : Result()
    }

    suspend fun execute(
    ): Result = try {
        settingsRepository.getCurrentNativeCurrency(
        )?.let {
            Result.Success(it)
        }
    }catch (e: IOException){
        Result.Error(e)
    }

}