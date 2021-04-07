package com.btavares.feature_portfolio.data

import com.btavares.feature_portfolio.MODULE_NAME
import com.btavares.feature_portfolio.data.repository.PortfolioRepositoryImpl
import com.btavares.feature_portfolio.domain.repository.PortfolioRepository
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton


internal  val dataModule = Kodein.Module("${MODULE_NAME}DataModule"){


    bind<PortfolioRepository>() with singleton { PortfolioRepositoryImpl(instance(), instance()) }

}