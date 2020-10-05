package com.btavares.feature_home.data.repository

import com.btavares.coins.app.data.sharepreference.UserSharePreferences
import com.btavares.feature_database.data.dao.UserDao
import com.btavares.feature_database.data.entities.*
import com.btavares.feature_home.data.model.NativeCurrencyDataModel
import com.btavares.feature_home.data.model.toDomainModel
import com.btavares.feature_home.data.remote.service.CoingeckoService
import com.btavares.feature_home.data.remote.service.CryptoControlNewsService
import com.btavares.feature_home.domain.model.NativeCurrencyDomainModel
import com.btavares.feature_home.domain.repository.HomeRepository

internal class HomeRepositoryImpl(
    private val userSharePreferences: UserSharePreferences,
    private val coingeckoService: CoingeckoService,
    private val cryptoControlNewsService: CryptoControlNewsService,
    private val userDao: UserDao) : HomeRepository {

     override suspend fun getMarketDataAsync(
         currency: String?,
         ids: String?
     ) = coingeckoService.getMarketDataAsync(
             currency,
             ids)
             .map { it.toDomainModel() }

    override suspend fun getCryptocurrenciesTopNewsAsync() =
        cryptoControlNewsService.getCryptocurrenciesNewsAsync(
        false
    ).map { it.toDomainModel() }

    override suspend fun getCryptocurrenciesLatestNewsAsync() =
        cryptoControlNewsService.getCryptocurrenciesNewsAsync(
            true
        ).map { it.toDomainModel() }

    override suspend fun getNativeCurrency(): NativeCurrencyDomainModel {
        val userCurrency = userDao.getUserNativeCurrency(userSharePreferences.getUserId())
        return NativeCurrencyDataModel(userCurrency.getCurrencyCode(), userCurrency.getCurrencySymbol()).toDomainModel()
    }

    override suspend fun getUserBalance(): Double =
         userDao.getUserBalance().portfolioBalance.total ?: 0.0


    override suspend fun getCryptocurrenciesWatchlistIds() =
        userDao.getUserCryptocurrencies(userSharePreferences.getUserId())

    override suspend fun checkIfUserExists() = userSharePreferences.checkIfUserInfoExists()

    override suspend fun insertUser(userFullName: String, userEmail: String) : Long {
        val userId = userDao.insertUser(User(null,userFullName,userEmail))
        setUpNewUserSettings(userId)
        return userId
    }

    private suspend fun setUpNewUserSettings(userId : Long?){
        if (userId != null && userId > 0){
            userDao.insertUserNativeCurrency(UserNativeCurrency(userId, "EUR"))
            userSharePreferences.saveUserId(userId)
        }
    }




}