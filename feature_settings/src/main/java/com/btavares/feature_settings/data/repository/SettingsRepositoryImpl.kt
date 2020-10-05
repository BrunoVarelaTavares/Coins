package com.btavares.feature_settings.data.repository

import com.btavares.coins.app.data.sharepreference.UserSharePreferences
import com.btavares.feature_database.data.dao.UserDao
import com.btavares.feature_database.data.entities.User
import com.btavares.feature_settings.data.model.NativeCurrencyDataModel
import com.btavares.feature_settings.data.model.UserDataModel
import com.btavares.feature_settings.data.model.toDomainModel
import com.btavares.feature_settings.domain.model.NativeCurrencyDomainModel
import com.btavares.feature_settings.domain.model.UserDomainModel
import com.btavares.feature_settings.domain.repository.SettingsRepository

class SettingsRepositoryImpl(
    private val userSharePreferences: UserSharePreferences,
    private val userDao: UserDao
) : SettingsRepository {

    override suspend fun getUser(): UserDomainModel {
      val user =  userDao.getUser(userSharePreferences.getUserId())
      return UserDataModel(user.name,user.email).toDomainModel()
    }

    override suspend fun getNativeCurrencies() =
         userDao.getNativeCurrencies().map { NativeCurrencyDataModel(it.currencyCode,
                                                        it.currencySymbol).toDomainModel() }

    override suspend fun getCurrentNativeCurrency(): NativeCurrencyDomainModel {
        val userCurrency = userDao.getUserNativeCurrency(userSharePreferences.getUserId())
        return NativeCurrencyDataModel(userCurrency.nativeCurrency.currencyCode,userCurrency.nativeCurrency.currencyCode).toDomainModel()
    }

    override suspend fun updateUserNativeCurrency(currencyCode: String) {
        return userDao.updateUserNativeCurrency(userSharePreferences.getUserId(), currencyCode)
    }

    override suspend fun updateUser(userFullName: String, userEmail: String) {
        val userId = userSharePreferences.getUserId()
        userDao.updateUser(User(userId, userFullName, userEmail))
    }


}