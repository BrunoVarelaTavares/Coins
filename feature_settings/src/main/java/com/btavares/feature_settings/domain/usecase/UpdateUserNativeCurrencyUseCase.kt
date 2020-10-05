package com.btavares.feature_settings.domain.usecase

import com.btavares.feature_settings.domain.repository.SettingsRepository

internal class UpdateUserNativeCurrencyUseCase(private val settingsRepository: SettingsRepository) {

    suspend fun execute(currencyCode: String) = settingsRepository.updateUserNativeCurrency(
            currencyCode
        )

}