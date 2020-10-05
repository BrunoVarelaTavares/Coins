package com.btavares.coins.app.data.retrofit

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptorCryptoNews(private val apiKey: String?) : Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response = chain.request().let {
        val url = it.url.newBuilder()
            .addQueryParameter("key", apiKey)
            .build()

        val newRequest = it.newBuilder()
            .url(url)
            .build()

        chain.proceed(newRequest)
    }
}