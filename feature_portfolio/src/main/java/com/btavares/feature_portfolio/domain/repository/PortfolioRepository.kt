package com.btavares.feature_portfolio.domain.repository

import com.btavares.feature_portfolio.domain.model.NativeCurrencyDomainModel
import com.btavares.feature_portfolio.domain.model.PortfolioCryptocurrencyDomainModel

internal interface PortfolioRepository {

    suspend fun getNativeCurrency() : NativeCurrencyDomainModel

    suspend fun getAllCryptocurrencies() : List<PortfolioCryptocurrencyDomainModel>

    suspend fun getPortfolioBalance() : Double

    suspend fun savePortfolioNativeCurrencyValue(cryptoId: String, nativeCurrencyValue : Double)

}