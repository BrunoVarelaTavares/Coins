package com.btavares.coins.app.data.retrofit

import okhttp3.Interceptor
import java.io.IOException

class AuthenticationInterceptor(defaultHost: String? = null) : Interceptor {
    @Volatile var host: String? = null

    init {
        host = defaultHost
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()

        this.host?.let {host->
            val urlBuilder = request.url.newBuilder()

            urlBuilder.host(host)

            request = request.newBuilder().url(urlBuilder.build()).build()
        }
        return chain.proceed(request)
    }
}
