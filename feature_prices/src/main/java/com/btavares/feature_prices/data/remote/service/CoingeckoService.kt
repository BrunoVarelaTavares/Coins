package com.btavares.feature_prices.data.remote.service

import com.btavares.feature_prices.data.model.CryptocurrencyMarketDataModel
import com.btavares.feature_prices.data.remote.response.GetGlobalMarketDataResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface CoingeckoService {

    @GET("global")
    suspend fun  getGlobalMarketDataAsync(
    ): GetGlobalMarketDataResponse

    @GET("coins/markets")
    suspend fun  getCryptocurrencyMarketDataAsync(
        @Query("vs_currency") currency: String?
    ): MutableList<CryptocurrencyMarketDataModel>

}