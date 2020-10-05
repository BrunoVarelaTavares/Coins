package com.btavares.feature_settings.domain.repository

import com.btavares.feature_settings.domain.model.NativeCurrencyDomainModel
import com.btavares.feature_settings.domain.model.UserDomainModel

interface SettingsRepository {

    suspend fun getUser() : UserDomainModel

    suspend fun getNativeCurrencies() : List<NativeCurrencyDomainModel>

    suspend fun getCurrentNativeCurrency() : NativeCurrencyDomainModel

    suspend fun updateUserNativeCurrency(currencyCode: String)

    suspend fun updateUser(userFullName: String, userEmail : String)
}