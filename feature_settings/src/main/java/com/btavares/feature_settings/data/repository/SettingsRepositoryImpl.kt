package com.btavares.feature_settings.data.repository

import com.btavares.coins.app.data.sharepreference.UserSharePreferences
import com.btavares.feature_database.data.dao.UserDao
import com.btavares.feature_database.data.entities.User
import com.btavares.feature_settings.data.model.NativeCurrencyDataModel
import com.btavares.feature_settings.data.model.UserDataModel
import com.btavares.feature_settings.data.model.toDomainModel
import com.btavares.feature_settings.data.remote.CoingeckoService
import com.btavares.feature_settings.data.remote.ExchangeRatesApiService
import com.btavares.feature_settings.domain.model.NativeCurrencyDomainModel
import com.btavares.feature_settings.domain.model.UserDomainModel
import com.btavares.feature_settings.domain.repository.SettingsRepository
import com.btavares.library_base.presentation.extension.round

internal class SettingsRepositoryImpl(
    private val userSharePreferences: UserSharePreferences,
    private val coingeckoService: CoingeckoService,
    private val userDao: UserDao,
    private val exchangeRatesApiService: ExchangeRatesApiService
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
        val userNativeCurrencyCode = userDao.getNativeUserCurrency(userSharePreferences.getUserId())
        val currenciesCall = "${userNativeCurrencyCode}_${currencyCode}"
        val call = exchangeRatesApiService.getRate(currenciesCall)
        val value = call.get(currenciesCall)
        val portfolio = userDao.getAllPortfolio()
        portfolio.forEach {
            if(it.nativeCurrencyValue!! > 0){
                val nativeCurrencyNewValue = it.nativeCurrencyValue!! * value!!
                userDao.updatePortfolioNativeCurrencyValue(it.portfolioId, round(nativeCurrencyNewValue))
            }
        }

        userDao.updateTotalBalance(userDao.getBalanceId(), userDao.portfolioTotal())
        return userDao.updateUserNativeCurrency(userSharePreferences.getUserId(), currencyCode)
    }

    override suspend fun updateUser(userFullName: String, userEmail: String) {
        val userId = userSharePreferences.getUserId()
        userDao.updateUser(User(userId, userFullName, userEmail))
    }


}