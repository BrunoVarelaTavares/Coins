package com.btavares.feature_home.data

import com.btavares.coins.app.data.retrofit.RetrofitCryptoNewsClient
import com.btavares.coins.app.data.retrofit.RetrofitCoingeckoClient
import com.btavares.feature_home.MODULE_NAME
import com.btavares.feature_home.data.remote.service.CoingeckoService
import com.btavares.feature_home.data.remote.service.CryptoControlNewsService
import com.btavares.feature_home.data.repository.HomeRepositoryImpl
import com.btavares.feature_home.domain.repository.HomeRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

internal  val dataModule = Kodein.Module("${MODULE_NAME}DataModule"){

    bind<HomeRepository>() with singleton { HomeRepositoryImpl(instance(),instance(),instance(),instance())}

    bind() from singleton { instance<RetrofitCoingeckoClient>().build().create(CoingeckoService::class.java)}

    bind() from singleton { instance<RetrofitCryptoNewsClient>().build().create(CryptoControlNewsService::class.java)}

}