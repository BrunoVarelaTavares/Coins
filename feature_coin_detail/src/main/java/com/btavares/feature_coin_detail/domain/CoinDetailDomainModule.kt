package com.btavares.feature_coin_detail.domain

import com.btavares.feature_coin_detail.MODULE_NAME
import com.btavares.feature_coin_detail.domain.usecase.*
import com.btavares.feature_coin_detail.domain.usecase.GetCryptocurrencyChartDataUseCase
import com.btavares.feature_coin_detail.domain.usecase.GetCryptocurrencyInfoUseCase
import com.btavares.feature_coin_detail.domain.usecase.GetCryptocurrencyMarketChartByRangeUseCase
import com.btavares.feature_coin_detail.domain.usecase.GetCryptocurrencyMarketDataUseCase
import com.btavares.feature_coin_detail.domain.usecase.GetUserNativeCurrencyUseCase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton


internal  val domainModule = Kodein.Module("${MODULE_NAME}DomainModule"){

    bind() from singleton { GetCryptocurrencyMarketDataUseCase(instance()) }

    bind() from singleton { GetUserNativeCurrencyUseCase(instance()) }

    bind() from singleton { GetCryptocurrencyChartDataUseCase(instance()) }

    bind() from singleton { GetCryptocurrencyMarketChartByRangeUseCase(instance()) }

    bind() from singleton { GetCryptocurrencyInfoUseCase(instance()) }

    bind() from singleton { GetNewsByCryptocurrencyUseCase(instance())}

    bind() from singleton { CheckIfCryptocurrencyExistsInWatchlistUseCase(instance())}

    bind() from singleton { InsertCryptocurrencyIntoWatchlistUseCase(instance()) }

    bind() from singleton { DeleteCryptocurrencyFromWatchlistUseCase(instance()) }
}