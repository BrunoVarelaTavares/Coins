package com.btavares.feature_home.data.remote.service


import com.btavares.feature_home.data.model.CryptocurrencyMarketDataModel
import retrofit2.http.GET
import retrofit2.http.Query

internal interface CoingeckoService {

    @GET("coins/markets")
    suspend fun  getMarketDataAsync(
        @Query("vs_currency") currency: String?,
        @Query("ids") ids: String?
    ): List<CryptocurrencyMarketDataModel>

}