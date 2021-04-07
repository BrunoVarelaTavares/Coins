package com.btavares.feature_settings.data

import com.btavares.coins.app.data.retrofit.RetrofitCoingeckoClient
import com.btavares.coins.app.data.retrofit.RetrofitCurrencyConverterApiClient
import com.btavares.feature_settings.MODULE_NAME
import com.btavares.feature_settings.data.remote.CoingeckoService
import com.btavares.feature_settings.data.remote.ExchangeRatesApiService
import com.btavares.feature_settings.data.repository.SettingsRepositoryImpl
import com.btavares.feature_settings.domain.repository.SettingsRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton


internal  val dataModule = Kodein.Module("${MODULE_NAME}DataModule"){

    bind<SettingsRepository>() with singleton { SettingsRepositoryImpl(instance(),instance(),instance(), instance()) }

    bind() from singleton { instance<RetrofitCoingeckoClient>().build().create(CoingeckoService::class.java)}

    bind() from singleton { instance<RetrofitCurrencyConverterApiClient>().build().create(
        ExchangeRatesApiService::class.java)}

}