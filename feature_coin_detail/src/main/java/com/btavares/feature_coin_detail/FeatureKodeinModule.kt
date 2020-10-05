package com.btavares.feature_coin_detail

import com.btavares.coins.app.feature.KodeinModuleProvider
import com.btavares.feature_coin_detail.data.dataModule
import com.btavares.feature_coin_detail.domain.domainModule
import com.btavares.feature_coin_detail.presentation.presentationModule
import org.kodein.di.Kodein


internal const val MODULE_NAME = "CoinDetail"

object FeatureKodeinModule : KodeinModuleProvider{

    override val kodeinModule = Kodein.Module("${MODULE_NAME}Module"){
        import(presentationModule)
        import(domainModule)
        import(dataModule)
    }



}

