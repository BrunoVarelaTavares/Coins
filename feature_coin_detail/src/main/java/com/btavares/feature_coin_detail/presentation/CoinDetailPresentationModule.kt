package com.btavares.feature_coin_detail.presentation

import androidx.fragment.app.Fragment
import com.btavares.feature_coin_detail.MODULE_NAME
import com.btavares.feature_coin_detail.presentation.cryptocurrencydetail.CryptocurrencyDetailViewModel
import com.btavares.feature_coin_detail.presentation.cryptocurrencydetail.recyclerview.NewsAdapter
import com.btavares.library_base.di.KotlinViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton


internal  val presentationModule = Kodein.Module("${MODULE_NAME}PresentationModule"){

    bind<CryptocurrencyDetailViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context){ CryptocurrencyDetailViewModel(instance(), instance(), instance(),instance(),instance(),instance(),instance(),instance(),instance()) }
    }

    bind() from singleton { NewsAdapter() }

}