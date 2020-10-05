package com.btavares.feature_database

import com.btavares.coins.app.feature.KodeinModuleProvider
import com.btavares.feature_database.data.dataModule
import org.kodein.di.Kodein

internal const val MODULE_NAME = "Database"

object FeatureKodeinModule : KodeinModuleProvider {

    override val kodeinModule = Kodein.Module("${MODULE_NAME}Module"){
        import(dataModule)
    }
}
