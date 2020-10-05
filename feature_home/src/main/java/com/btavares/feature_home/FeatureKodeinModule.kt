package com.btavares.feature_home

import com.btavares.coins.app.feature.KodeinModuleProvider
import com.btavares.feature_home.data.dataModule
import com.btavares.feature_home.domain.domainModule
import com.btavares.feature_home.presentation.presentationModule
import org.kodein.di.Kodein


internal const val MODULE_NAME = "Home"

object FeatureKodeinModule : KodeinModuleProvider{

    override val kodeinModule = Kodein.Module("${MODULE_NAME}Module"){
        import(presentationModule)
        import(domainModule)
        import(dataModule)
    }



}

