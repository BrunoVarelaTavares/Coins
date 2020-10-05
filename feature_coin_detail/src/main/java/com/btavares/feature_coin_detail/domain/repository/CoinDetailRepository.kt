package com.btavares.feature_coin_detail.domain.repository

import com.btavares.feature_coin_detail.domain.model.*
import com.btavares.feature_coin_detail.domain.model.CryptocurrencyMarketChartDomainModel
import com.btavares.feature_coin_detail.domain.model.CryptocurrencyMarketDomainModel
import com.btavares.feature_coin_detail.domain.model.NativeCurrencyDomainModel
import com.btavares.feature_coin_detail.domain.model.NewsDomainModel

internal interface CoinDetailRepository {

    suspend fun getNativeCurrency() : NativeCurrencyDomainModel

    suspend fun getCryptocurrencyMarketData(currency : String) : MutableList<CryptocurrencyMarketDomainModel>

    suspend fun  getCryptocurrencyMarketChart(
        id : String,
        currency: String?,
        days: String?
    ) : CryptocurrencyMarketChartDomainModel?

    suspend fun  getCryptocurrencyMarketChartByRange(
        id : String,
        currency: String?,
        fromTimestamp : String,
        toTimestamp : String
    ) : CryptocurrencyMarketChartDomainModel?


    suspend fun getNewsByCryptocurrencyAsync(
        coinId: String
    ): List<NewsDomainModel>

    suspend fun getCryptocurrenciesNewsAsync(
        latestNews: Boolean?,
        language : String?
    ): List<NewsDomainModel>

    suspend fun getCryptocurrencyInfoAsync(
        id : String
    ): CryptocurrencyDomainModel?

    suspend fun getCryptocurrenciesWatchlistIds (): List<String>

    suspend fun checkCryptocurrencyExistsInWatchlist(cryptocurrencyId : String) : Boolean

    suspend fun insertCryptocurrencyInWatchlist(cryptocurrencyId: String)

    suspend fun deleteCryptocurrencyInWatchlist(cryptocurrencyId: String)
}