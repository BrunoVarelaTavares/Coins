package com.btavares.feature_coin_detail.data.remote.service

import com.btavares.feature_coin_detail.data.model.CryptocurrencyDataModel
import com.btavares.feature_coin_detail.data.model.CryptocurrencyMarketChartDataModel
import com.btavares.feature_coin_detail.data.model.CryptocurrencyMarketDataModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface CoingeckoService {



    @GET("coins/markets")
    suspend fun  getCryptocurrencyMarketDataAsync(
        @Query("vs_currency") currency: String?
    ): MutableList<CryptocurrencyMarketDataModel>

    @GET("coins/{id}/market_chart")
    suspend fun getCryptocurrencyMarketChartAsync(
        @Path("id") id : String,
        @Query("vs_currency") currency: String?,
        @Query("days") days: String?
    ) : CryptocurrencyMarketChartDataModel?

    @GET("coins/{id}/market_chart/range")
    suspend fun getCryptocurrencyMarketChartByRangeAsync(
        @Path("id") id : String,
        @Query("vs_currency") currency: String?,
        @Query("from") fromTimestamp : String,
        @Query("to") toTimestamp : String
    ) : CryptocurrencyMarketChartDataModel?


    @GET("coins/{id}?localization=false&tickers=false&market_data=false&community_data=false&developer_data=false&sparkline=false")
    suspend fun getCryptocurrencyInfoAsync(
        @Path("id") id : String
    ) : CryptocurrencyDataModel?

}