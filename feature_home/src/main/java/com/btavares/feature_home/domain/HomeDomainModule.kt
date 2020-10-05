package com.btavares.feature_home.domain

import com.btavares.feature_home.MODULE_NAME
import com.btavares.feature_home.domain.usecase.*
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

internal  val domainModule = Kodein.Module("${MODULE_NAME}DomainModule"){

    bind() from singleton { CheckIfUserExistsUseCase(instance())}

    bind() from singleton { GetMarketDataUseCase(instance()) }

    bind() from singleton { GetNewsUseCase(instance()) }

    bind() from singleton { GetLatestNewsUseCase(instance()) }

    bind() from singleton { GetUserNativeCurrencyUseCase(instance())}

    bind() from singleton { GetUserPortfolioBalanceUseCase(instance())}

    bind() from singleton { GetCryptocurrenciesWatchlistIds(instance())}

    bind() from singleton { InsertUserUseCase(instance()) }
}