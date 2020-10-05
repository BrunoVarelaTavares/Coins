package com.btavares.feature_settings

import com.btavares.coins.app.feature.KodeinModuleProvider
import com.btavares.feature_settings.data.dataModule
import com.btavares.feature_settings.domain.domainModule
import com.btavares.feature_settings.presentation.presentationModule
import org.kodein.di.Kodein

internal const val MODULE_NAME = "Settings"

object FeatureKodeinModule : KodeinModuleProvider {


    override val kodeinModule = Kodein.Module("${MODULE_NAME}Module"){
        import(presentationModule)
        import(domainModule)
        import(dataModule)
    }



}