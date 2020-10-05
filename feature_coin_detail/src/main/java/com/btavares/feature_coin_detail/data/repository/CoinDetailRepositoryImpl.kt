package com.btavares.feature_coin_detail.data.repository

import com.btavares.coins.app.data.sharepreference.UserSharePreferences
import com.btavares.feature_coin_detail.data.model.NativeCurrencyDataModel
import com.btavares.feature_coin_detail.data.model.toDomainModel
import com.btavares.feature_coin_detail.data.remote.service.CoingeckoService
import com.btavares.feature_coin_detail.data.remote.service.CryptoControlNewsService
import com.btavares.feature_coin_detail.domain.model.GlobalMarketDataDomainModel
import com.btavares.feature_coin_detail.domain.model.NativeCurrencyDomainModel
import com.btavares.feature_coin_detail.domain.repository.CoinDetailRepository
import com.btavares.feature_database.data.dao.UserDao
import com.btavares.feature_database.data.entities.UserCryptocurrency
import com.btavares.feature_database.data.entities.getCurrencyCode
import com.btavares.feature_database.data.entities.getCurrencySymbol

internal class CoinDetailRepositoryImpl (
    private val userSharePreferences: UserSharePreferences,
    private val coingeckoService: CoingeckoService,
    private val cryptoControlNewsService: CryptoControlNewsService,
    private val userDao: UserDao
): CoinDetailRepository {

    override suspend fun getCryptocurrencyMarketData(currency: String)
            = coingeckoService.getCryptocurrencyMarketDataAsync(currency).map { it.toDomainModel() }.toMutableList()

    override suspend fun getCryptocurrencyMarketChart(
        id: String,
        currency: String?,
        days: String?) = coingeckoService.getCryptocurrencyMarketChartAsync(
        id,
        currency,
        days)
        ?.toDomainModel()

    override suspend fun getCryptocurrencyMarketChartByRange(
        id: String,
        currency: String?,
        fromTimestamp : String,
        toTimestamp : String
    )  = coingeckoService.getCryptocurrencyMarketChartByRangeAsync(
        id,
        currency,
        fromTimestamp,
        toTimestamp
    )?.toDomainModel()


    override suspend fun getCryptocurrencyInfoAsync(
        id: String
    ) = coingeckoService.getCryptocurrencyInfoAsync(
        id
    )?.toDomainModel()


    override suspend fun getNewsByCryptocurrencyAsync(
        coinId: String
    ) = cryptoControlNewsService.getNewsByCryptocurrencyAsync(
        coinId,
        false,
        ""
    ).map { it.toDomainModel() }

    override suspend fun getCryptocurrenciesNewsAsync(
        latestNews: Boolean?,
        language: String?
    ) =
        cryptoControlNewsService.getCryptocurrenciesNewsAsync(
            latestNews,
            language
        ).map { it.toDomainModel() }



    override suspend fun getNativeCurrency(): NativeCurrencyDomainModel {
        val userCurrency = userDao.getUserNativeCurrency(userSharePreferences.getUserId())
        return NativeCurrencyDataModel(userCurrency.getCurrencyCode(), userCurrency.getCurrencySymbol()).toDomainModel()
    }

    override suspend fun getCryptocurrenciesWatchlistIds()
            = userDao.getUserCryptocurrencies(userSharePreferences.getUserId())


    override suspend fun checkCryptocurrencyExistsInWatchlist(cryptocurrencyId : String) =
        userDao.checkIfCryptocurrencyExistsInWatchlist(userSharePreferences.getUserId(), cryptocurrencyId)

    override suspend fun insertCryptocurrencyInWatchlist(cryptocurrencyId: String) {
        userDao.insertCryptocurrencyInWatchlist(UserCryptocurrency(userSharePreferences.getUserId(), cryptocurrencyId))
    }

    override suspend fun deleteCryptocurrencyInWatchlist(cryptocurrencyId: String) {
        userDao.deleteCryptocurrencyInWatchlist(UserCryptocurrency(userSharePreferences.getUserId(), cryptocurrencyId))
    }

}