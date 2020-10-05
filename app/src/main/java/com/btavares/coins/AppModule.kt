package com.btavares.coins

import com.btavares.coins.app.data.retrofit.*
import com.btavares.coins.app.data.sharepreference.UserSharePreferences
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal const val MODULE_NAME = "App"

val appModule = Kodein.Module("${MODULE_NAME}Module") {

    bind() from singleton { AuthenticationInterceptorMarketData() }

    bind() from singleton { AuthenticationInterceptorCryptoNews(BuildConfig.GRADLE_CRYPTOCONTROL_API_KEY) }

    bind() from singleton { UserAgentInterceptor() }

    bind() from singleton { RetrofitCoingeckoClient(instance(),instance(),instance()) }

    bind() from singleton { RetrofitCryptoNewsClient(instance(),instance(),instance()) }

    bind() from singleton {UserSharePreferences(instance())}

    bind<HttpLoggingInterceptor>() with singleton {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    bind<Retrofit.Builder>() with singleton { Retrofit.Builder() }

    bind<OkHttpClient.Builder>() with singleton { OkHttpClient.Builder() }

    bind<OkHttpClient>() with singleton {
        instance<OkHttpClient.Builder>()
            .addNetworkInterceptor(StethoInterceptor())
            .addInterceptor(instance<AuthenticationInterceptorMarketData>())
            .addInterceptor(instance<UserAgentInterceptor>())
            .addInterceptor(instance<HttpLoggingInterceptor>())
            .build()
    }

    bind<Retrofit>() with singleton {
        instance<Retrofit.Builder>()
            .baseUrl(BuildConfig.GRADLE_COINGECKO_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(instance())
            .build()
    }

}