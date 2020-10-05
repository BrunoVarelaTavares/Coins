package com.btavares.feature_settings.domain.usecase

import com.btavares.feature_settings.domain.model.NativeCurrencyDomainModel
import com.btavares.feature_settings.domain.repository.SettingsRepository

class GetNativeCurrenciesUseCase(private val settingsRepository: SettingsRepository) {

    sealed class Result{
        data class Success(val data: List<NativeCurrencyDomainModel>) : Result()
        data class Error(val e: Throwable) : Result()
    }
    suspend fun execute() = settingsRepository.getNativeCurrencies()

}