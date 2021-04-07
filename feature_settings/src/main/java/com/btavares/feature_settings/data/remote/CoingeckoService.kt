package com.btavares.feature_settings.data.remote

import com.btavares.feature_settings.data.model.CryptocurrencyMarketDataModel
import retrofit2.http.GET
import retrofit2.http.Query

internal interface CoingeckoService {

    @GET("coins/markets")
    suspend fun  getMarketDataAsync(
        @Query("vs_currency") currency: String?,
        @Query("ids") ids: String? = ""
    ): List<CryptocurrencyMarketDataModel>

}