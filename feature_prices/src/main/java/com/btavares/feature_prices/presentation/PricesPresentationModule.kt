package com.btavares.feature_prices.presentation

import androidx.fragment.app.Fragment
import com.btavares.feature_prices.MODULE_NAME
import com.btavares.feature_prices.presentation.prices.PricesViewModel
import com.btavares.feature_prices.presentation.prices.recyclerview.CryptocurrencyMarketDataAdapter
import com.btavares.feature_prices.presentation.prices.recyclerview.SearchCryptocurrencyAdapter
import com.btavares.library_base.di.KotlinViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.*


internal  val presentationModule = Kodein.Module("${MODULE_NAME}PresentationModule"){

    bind<PricesViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context){PricesViewModel(instance(),instance(), instance(), instance())}
    }

    bind() from provider { CryptocurrencyMarketDataAdapter() }

    bind() from singleton { SearchCryptocurrencyAdapter() }

}