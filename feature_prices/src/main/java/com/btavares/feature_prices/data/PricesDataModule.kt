package com.btavares.feature_prices.data

import com.btavares.coins.app.data.retrofit.RetrofitCoingeckoClient
import com.btavares.feature_prices.MODULE_NAME
import com.btavares.feature_prices.data.remote.service.CoingeckoService
import com.btavares.feature_prices.data.repository.PricesRepositoryImpl
import com.btavares.feature_prices.domain.repository.PricesRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton


internal  val dataModule = Kodein.Module("${MODULE_NAME}DataModule"){

    bind<PricesRepository>() with singleton { PricesRepositoryImpl(instance(),
        instance(),instance()) }

    bind() from singleton { instance<RetrofitCoingeckoClient>().build().create(
        CoingeckoService::class.java) }

}