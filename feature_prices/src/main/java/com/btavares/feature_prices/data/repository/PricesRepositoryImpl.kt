package com.btavares.feature_prices.data.repository

import com.btavares.coins.app.data.sharepreference.UserSharePreferences
import com.btavares.feature_database.data.dao.UserDao
import com.btavares.feature_database.data.entities.getCurrencyCode
import com.btavares.feature_database.data.entities.getCurrencySymbol
import com.btavares.feature_prices.data.model.NativeCurrencyDataModel
import com.btavares.feature_prices.data.model.toDomainModel
import com.btavares.feature_prices.data.remote.service.CoingeckoService
import com.btavares.feature_prices.domain.model.GlobalMarketDataDomainModel
import com.btavares.feature_prices.domain.model.NativeCurrencyDomainModel
import com.btavares.feature_prices.domain.repository.PricesRepository

internal class PricesRepositoryImpl(
    private val userSharePreferences: UserSharePreferences,
    private val coingeckoService: CoingeckoService,
    private val userDao: UserDao
): PricesRepository {

    override suspend fun getGlobalPrices() : GlobalMarketDataDomainModel{
        return coingeckoService.getGlobalMarketDataAsync().data.toDomainModel()
    }

    override suspend fun getCryptocurrencyMarketData(currency: String)
        = coingeckoService.getCryptocurrencyMarketDataAsync(currency).map { it.toDomainModel() }.toMutableList()

    override suspend fun getNativeCurrency(): NativeCurrencyDomainModel {
        val userCurrency = userDao.getUserNativeCurrency(userSharePreferences.getUserId())
        return NativeCurrencyDataModel(userCurrency.getCurrencyCode(), userCurrency.getCurrencySymbol()).toDomainModel()
    }

}