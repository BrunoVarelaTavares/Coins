package com.btavares.feature_coin_detail.data

import com.btavares.coins.app.data.retrofit.RetrofitCoingeckoClient
import com.btavares.coins.app.data.retrofit.RetrofitCryptoNewsClient
import com.btavares.feature_coin_detail.MODULE_NAME
import com.btavares.feature_coin_detail.data.remote.service.CoingeckoService
import com.btavares.feature_coin_detail.data.remote.service.CryptoControlNewsService
import com.btavares.feature_coin_detail.data.repository.CoinDetailRepositoryImpl
import com.btavares.feature_coin_detail.domain.repository.CoinDetailRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton


internal  val dataModule = Kodein.Module("${MODULE_NAME}DataModule"){

    bind<CoinDetailRepository>() with singleton { CoinDetailRepositoryImpl(instance(),
        instance(),instance(),instance()) }

    bind() from singleton { instance<RetrofitCoingeckoClient>().build().create(
        CoingeckoService::class.java) }

    bind() from singleton { instance<RetrofitCryptoNewsClient>().build().create(
        CryptoControlNewsService::class.java) }


}