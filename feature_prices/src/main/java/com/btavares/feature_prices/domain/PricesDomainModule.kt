package com.btavares.feature_prices.domain

import com.btavares.feature_prices.MODULE_NAME
import com.btavares.feature_prices.domain.usecase.GetCryptocurrencyMarketDataUseCase
import com.btavares.feature_prices.domain.usecase.GetGlobalMarketDataUseCase
import com.btavares.feature_prices.domain.usecase.GetUserNativeCurrencyUseCase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton


internal  val domainModule = Kodein.Module("${MODULE_NAME}DomainModule"){

    bind() from singleton { GetGlobalMarketDataUseCase(instance()) }

    bind() from singleton { GetCryptocurrencyMarketDataUseCase(instance()) }

    bind() from singleton { GetUserNativeCurrencyUseCase(instance()) }

}