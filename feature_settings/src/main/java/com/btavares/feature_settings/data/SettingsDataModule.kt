package com.btavares.feature_settings.data

import com.btavares.feature_settings.MODULE_NAME
import com.btavares.feature_settings.data.repository.SettingsRepositoryImpl
import com.btavares.feature_settings.domain.repository.SettingsRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton


internal  val dataModule = Kodein.Module("${MODULE_NAME}DataModule"){

    bind<SettingsRepository>() with singleton { SettingsRepositoryImpl(instance(),instance()) }

}