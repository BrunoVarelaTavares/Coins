package com.btavares.feature_settings.data.remote

import com.btavares.feature_settings.data.model.RatesDataModel
import retrofit2.http.GET

import retrofit2.http.Query

internal interface ExchangeRatesApiService {

    @GET("convert?")
    suspend fun  getRate(
        @Query("q") currentCurrencyCode : String,
        @Query("compact") compact : String = "ultra"
    ): Map<String, Double>

}