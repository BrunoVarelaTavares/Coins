package com.btavares.feature_home.domain.repository

import com.btavares.feature_home.domain.model.*

internal interface HomeRepository {

    suspend fun getMarketDataAsync(
        currency: String?,
        ids: String?
    ): List<CryptocurrencyMarketDomainModel>

    suspend fun getCryptocurrenciesTopNewsAsync(
    ): List<NewsDomainModel>

    suspend fun getCryptocurrenciesLatestNewsAsync(
    ): List<NewsDomainModel>?

    suspend fun getNativeCurrency() : NativeCurrencyDomainModel

    suspend fun getUserBalance() : Double

    suspend fun getCryptocurrenciesWatchlistIds (): List<String>

    suspend fun checkIfUserExists() : Boolean

    suspend fun insertUser(userFullName :String, userEmail: String) : Long

}