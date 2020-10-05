package com.btavares.feature_prices

import com.btavares.coins.app.feature.KodeinModuleProvider
import com.btavares.feature_prices.data.dataModule
import com.btavares.feature_prices.domain.domainModule
import com.btavares.feature_prices.presentation.presentationModule
import org.kodein.di.Kodein

internal const val MODULE_NAME = "Prices"

object FeatureKodeinModule : KodeinModuleProvider {

    override val kodeinModule = Kodein.Module("${MODULE_NAME}Module"){
        import(presentationModule)
        import(domainModule)
        import(dataModule)
    }



}