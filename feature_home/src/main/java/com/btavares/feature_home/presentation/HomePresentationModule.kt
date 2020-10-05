package com.btavares.feature_home.presentation

import androidx.fragment.app.Fragment
import coil.ImageLoader
import com.btavares.feature_home.MODULE_NAME
import com.btavares.feature_home.presentation.home.HomeViewModel
import com.btavares.feature_home.presentation.home.recyclerview.MarketDataFavoritesAdapter
import com.btavares.feature_home.presentation.home.recyclerview.MarketDataTopMoversAdapter
import com.btavares.feature_home.presentation.home.recyclerview.NewsAdapter
import com.btavares.feature_home.presentation.news.NewsViewModel
import com.btavares.feature_home.presentation.notifications.NotificationsViewModel
import com.btavares.feature_home.presentation.registration.RegistrationViewModel
import com.btavares.library_base.di.KotlinViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton

internal  val presentationModule = Kodein.Module("${MODULE_NAME}PresentationModule"){

    bind<HomeViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context){HomeViewModel(instance(),instance(),instance(),instance(),instance(),instance(),instance())}
    }

    bind<RegistrationViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context){RegistrationViewModel(instance(), instance())}
    }

    bind<NewsViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context){NewsViewModel(instance(), instance())}
    }

    bind<NotificationsViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context){NotificationsViewModel(instance())}
    }


    bind() from singleton { MarketDataFavoritesAdapter() }

    bind() from singleton { MarketDataTopMoversAdapter() }

    bind() from singleton { NewsAdapter() }

    bind() from singleton { ImageLoader(instance()) }

}