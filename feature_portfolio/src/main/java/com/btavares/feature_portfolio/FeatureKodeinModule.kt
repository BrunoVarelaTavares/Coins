package com.btavares.feature_portfolio

import com.btavares.coins.app.feature.KodeinModuleProvider
import com.btavares.feature_portfolio.data.dataModule
import com.btavares.feature_portfolio.domain.domainModule
import com.btavares.feature_portfolio.presentation.presentationModule
import org.kodein.di.Kodein

internal const val MODULE_NAME = "Portfolio"

object FeatureKodeinModule : KodeinModuleProvider {


    override val kodeinModule = Kodein.Module("${MODULE_NAME}Module"){
        import(presentationModule)
        import(domainModule)
        import(dataModule)
    }



}