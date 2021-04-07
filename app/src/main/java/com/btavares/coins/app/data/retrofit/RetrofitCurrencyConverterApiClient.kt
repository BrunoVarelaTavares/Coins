package com.btavares.coins.app.data.retrofit

import com.btavares.coins.BuildConfig
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitCurrencyConverterApiClient(private val userAgentInterceptor: UserAgentInterceptor,
                                         private val authenticationInterceptorCurrencyConverter: AuthenticationInterceptorCurrencyConverter,
                                         private val httpLoggingInterceptor: HttpLoggingInterceptor
) {

    private fun instance() = OkHttpClient.Builder()
        .addNetworkInterceptor(StethoInterceptor())
        .addInterceptor(authenticationInterceptorCurrencyConverter)
        .addInterceptor(userAgentInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .build()


    fun build() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.GRADLE_CURRENCYCONVERTER_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(instance())
            .build()
    }

}