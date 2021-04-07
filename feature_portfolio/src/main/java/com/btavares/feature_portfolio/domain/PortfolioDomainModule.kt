package com.btavares.feature_portfolio.domain

import com.btavares.feature_portfolio.MODULE_NAME
import com.btavares.feature_portfolio.domain.usecase.GetAllPortfolioCryptocurrencies
import com.btavares.feature_portfolio.domain.usecase.GetPortfolioBalance
import com.btavares.feature_portfolio.domain.usecase.GetUserNativeCurrencyUseCase
import com.btavares.feature_portfolio.domain.usecase.SavePortfolioCurrencyValueUseCase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton


internal  val domainModule = Kodein.Module("${MODULE_NAME}DomainModule"){

    bind() from singleton { GetAllPortfolioCryptocurrencies(instance())}

    bind() from singleton { GetPortfolioBalance(instance())}

    bind() from singleton { GetUserNativeCurrencyUseCase(instance())}

    bind() from singleton { SavePortfolioCurrencyValueUseCase(instance()) }

}
