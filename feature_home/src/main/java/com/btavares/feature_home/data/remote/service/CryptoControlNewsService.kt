package com.btavares.feature_home.data.remote.service

import com.btavares.feature_home.data.model.NewsDataModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface  CryptoControlNewsService {

    @GET("public/news")
    suspend fun  getCryptocurrenciesNewsAsync(
        @Query("latest") latestNews: Boolean?
    ): List<NewsDataModel>


    @GET("public/news/coin/{coinId}")
    suspend fun  getNewsByCryptocurrencyAsync(
        @Path("coinId") id : String,
        @Query("latest") latestNews: Boolean?,
        @Query("language") language : String?

    ): List<NewsDataModel>

}