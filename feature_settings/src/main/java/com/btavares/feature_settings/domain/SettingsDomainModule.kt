package com.btavares.feature_settings.domain

import com.btavares.feature_settings.MODULE_NAME
import com.btavares.feature_settings.domain.usecase.*
import com.btavares.feature_settings.domain.usecase.GetCurrentNativeCurrencyUseCase
import com.btavares.feature_settings.domain.usecase.UpdateUserNativeCurrencyUseCase
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton


internal  val domainModule = Kodein.Module("${MODULE_NAME}DomainModule"){

    bind() from singleton { GetUserUseCase(instance())}

    bind() from singleton { GetNativeCurrenciesUseCase(instance())}

    bind() from singleton { GetCurrentNativeCurrencyUseCase(instance())}

    bind() from singleton { UpdateUserNativeCurrencyUseCase(instance())}

    bind() from singleton { UpdateUserUseCase(instance()) }

}