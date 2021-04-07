package com.btavares.feature_portfolio.presentation

import androidx.fragment.app.Fragment
import com.btavares.feature_portfolio.MODULE_NAME
import com.btavares.feature_portfolio.presentation.editcryptocurrency.EditCryptocurrencyViewModel
import com.btavares.feature_portfolio.presentation.portfolio.PortfolioViewModel
import com.btavares.feature_portfolio.presentation.portfolio.recyclerview.PortfolioCryptocurrencyAdapter
import com.btavares.library_base.di.KotlinViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton


internal val presentationModule = Kodein.Module("${MODULE_NAME}PresentationModule"){

    bind<PortfolioViewModel>()with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context){PortfolioViewModel(instance(),instance(),instance(),instance())}
    }

    bind<EditCryptocurrencyViewModel>()with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context){EditCryptocurrencyViewModel(instance(), instance(), instance())}
    }

    bind() from singleton { PortfolioCryptocurrencyAdapter() }


}