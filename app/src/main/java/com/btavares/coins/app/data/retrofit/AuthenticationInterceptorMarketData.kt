package com.btavares.coins.app.data.retrofit

import okhttp3.Interceptor
import java.io.IOException

class AuthenticationInterceptorMarketData(defaultHost: String? = null) : Interceptor {
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


/*
    override fun intercept(chain: Interceptor.Chain): Response = chain.request().let {
        val url = it.url.newBuilder()
            .build()

        val newRequest = it.newBuilder()
            .url(url)
            .build()
        chain.proceed(newRequest)

    }
*/

}


/*@Volatile*/
/*
private var host: HttpUrl? = null

fun setHost(host: String) {
    this.host = host.toHttpUrlOrNull()
}

fun clear() {
    host = null
}

override fun intercept(chain: Interceptor.Chain): Response = chain.request().let {
    var request = chain.request()

    host?.let {
        val newUrl = request.url.newBuilder()
            .scheme(it.scheme)
            .host(it.toUrl().toURI().host)
            .port(it.port)
            .build()
        request = request.newBuilder().url(newUrl).build()

    }


*/
/*        val url = it.url.newBuilder()
            .build()

        val newRequest = it.newBuilder()
            .header("Content-Type", "application/json")
            .url(url)
            .build()*//*


    chain.proceed(request)

}

*/