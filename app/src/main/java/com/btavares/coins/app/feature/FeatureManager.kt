package com.btavares.coins.app.feature

import com.btavares.coins.BuildConfig

@Suppress("detekt.UnsafeCast")
object FeatureManager {

    private const val featurePackagePrefix = "com.btavares.feature_"

    val kodeinModules = BuildConfig.FEATURE_MODULE_NAMES
        .map { "$featurePackagePrefix$it.FeatureKodeinModule" }
        .map {
            try {
                Class.forName(it).kotlin.objectInstance as KodeinModuleProvider
            } catch (e: ClassNotFoundException) {
                throw ClassNotFoundException("Kodein module class not found $it")
            }
        }
        .map { it.kodeinModule }
}