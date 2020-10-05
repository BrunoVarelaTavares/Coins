package com.btavares.coins.app
import android.content.Context
import com.btavares.coins.BuildConfig
import com.btavares.coins.app.feature.FeatureManager
import com.btavares.coins.app.kodein.FragmentArgsExternalSource
import com.btavares.coins.appModule
import com.btavares.library_base.baseModule
import com.facebook.stetho.Stetho
import com.google.android.play.core.splitcompat.SplitCompatApplication
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import timber.log.Timber

 class CoinsApplication : SplitCompatApplication(), KodeinAware {

    override val kodein = Kodein.lazy {
        import(androidXModule(this@CoinsApplication))
        import(baseModule)
        import(appModule)
        importAll(FeatureManager.kodeinModules)

        externalSources.add(FragmentArgsExternalSource())
    }


    private lateinit var context: Context


    override fun onCreate() {
        super.onCreate()

        context = this


        initTimber()
       // initStetho()
    }

    private fun initStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}