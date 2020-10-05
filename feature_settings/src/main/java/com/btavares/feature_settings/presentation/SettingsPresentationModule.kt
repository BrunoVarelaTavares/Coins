package com.btavares.feature_settings.presentation

import androidx.fragment.app.Fragment
import com.btavares.feature_settings.MODULE_NAME
import com.btavares.feature_settings.presentation.profile.ProfileViewModel
import com.btavares.feature_settings.presentation.settings.SettingsViewModel
import com.btavares.library_base.di.KotlinViewModelProvider
import org.kodein.di.Kodein
import org.kodein.di.android.x.AndroidLifecycleScope
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.scoped
import org.kodein.di.generic.singleton


internal  val presentationModule = Kodein.Module("${MODULE_NAME}PresentationModule"){

    bind<SettingsViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context){SettingsViewModel(instance(),instance(),instance(), instance(),instance())}
    }

    bind<ProfileViewModel>() with scoped<Fragment>(AndroidLifecycleScope).singleton {
        KotlinViewModelProvider.of(context){ProfileViewModel(instance(),instance(),instance())}
    }


}