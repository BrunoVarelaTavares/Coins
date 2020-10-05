package com.btavares.feature_prices.domain.repository

import com.btavares.feature_prices.domain.model.*
import com.btavares.feature_prices.domain.model.CryptocurrencyMarketDomainModel
import com.btavares.feature_prices.domain.model.NativeCurrencyDomainModel

internal interface PricesRepository {

    suspend fun getGlobalPrices() : GlobalMarketDataDomainModel

    suspend fun getNativeCurrency() : NativeCurrencyDomainModel

    suspend fun getCryptocurrencyMarketData(currency : String) : MutableList<CryptocurrencyMarketDomainModel>

}