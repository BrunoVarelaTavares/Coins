package com.btavares.feature_coin_detail.data

import com.btavares.feature_coin_detail.data.model.*
import com.btavares.feature_coin_detail.domain.model.CryptocurrencyDomainModel
import com.btavares.feature_coin_detail.domain.model.CryptocurrencyLinksDomainModel


object DataFixtures {

    internal fun getCryptocurrencyMarketData(
        id : String? = "bitcoin",
        symbol : String? = "btc" ,
        name : String? = "Bitcoin",
        image : String? = "https://assets.coingecko.com/coins/images/1/large/bitcoin.png?1547033579",
        currentPrice : Double? = 8910.55,
        marketCap : Long? = 164131002915,
        marketCapRank : Long? = 1,
        totalVolume : Double? = 186326046192.00,
        highTwentyFourHours : Double? = 17639239727.55,
        lowTwentyFourHours : Double? = 8708.45,
        priceChangeTwentyFourHours : Double?= -46.71737604,
        priceChangePercentageTwentyFourHours : Double? = -0.52158,
        marketCapChangeTwentyFourHours : Double? = -1582820637.6192324,
        marketCapChangePercentageTwentyFourHours : Double? = -0.95515,
        circulatingSupply : Double? = 18498493.0,
        totalSupply : Double? = 21000000.0,
        ath : Double? = 16727.68,
        athChangePercentage : Double? = -46.95816,
        athDate : String? = "2017-12-16T00:00:00.000Z",
        atl : Double? = 51.3,
        atlChangePercentage : Double? = 17196.19026,
        atlDate : String? = "2013-07-05T00:00:00.000Z" ,
        lastUpdated : String? = "2020-09-24T11:08:30.079Z"
    ): CryptocurrencyMarketDataModel = CryptocurrencyMarketDataModel(
        id,
        symbol,
        name,
        image,
        currentPrice,
        marketCap,
        marketCapRank,
        totalVolume,
        highTwentyFourHours,
        lowTwentyFourHours,
        priceChangeTwentyFourHours,
        priceChangePercentageTwentyFourHours,
        marketCapChangeTwentyFourHours,
        marketCapChangePercentageTwentyFourHours,
        circulatingSupply,
        totalSupply,
        ath,
        athChangePercentage,
        athDate,
        atl,
        atlChangePercentage,
        atlDate,
        lastUpdated)


    internal fun getNewsData(
        id : String? = "id",
        hotness : Double? = 0.0,
        activityHotness : Double? = 0.0,
        primaryCategory : String? = "primaryCategory",
        words : Int = 0,
        similarArticlesDomainModel : List<SimilarArticlesDataModel>? = listOf(),
        coins : List<NewsCoinsDataModel> = listOf(getNewsCoinsDataModel()),
        description : String? = "Description",
        publishedAt : String? = "publishedAt",
        title : String? = "Title",
        url : String = "url",
        sourceDomainModel : SourceDataModel? = null,
        thumbnail : String? = "thumbnail",
        sourceDomain : String? = "sourceDomain",
        originalImageUrl : String? = "originalImageUrl"
    ) : NewsDataModel = NewsDataModel(
        id,
        hotness,
        activityHotness,
        primaryCategory,
        words,
        similarArticlesDomainModel,
        coins,
        description,
        publishedAt,
        title,
        url,
        sourceDomainModel,
        thumbnail,
        sourceDomain,
        originalImageUrl)


    private fun getNewsCoinsDataModel(
        id : String? = "id",
        name : String? = "Bitcoin",
        tradingSymbol : String? = "BTC",
        slug : String? = "bitcoin"
    ): NewsCoinsDataModel = NewsCoinsDataModel(id!!,name!!,tradingSymbol!!,slug!!)


    internal fun getCryptocurrencyMarketChartDataModel(
        prices : List<List<Double>> = listOf(listOf(1.601552362645E12,10888.62595065162)),
        market_caps : List<List<Double>> = listOf(listOf(1.601552362645E12,2.0106606260699362E11)),
        total_volumes : List<List<Double>> = listOf(listOf(1.601552362645E12,2.0106606260699362E11))
    ): CryptocurrencyMarketChartDataModel = CryptocurrencyMarketChartDataModel(prices,market_caps, total_volumes)

    internal fun getTotalVolumeMarketChartDataModel(
        date: String? = "10/01/2020 13:39",
        totalVolume: Double = 2.0106606260699362E11
    ) : TotalVolumeMarketChartDataModel = TotalVolumeMarketChartDataModel(date, totalVolume)

    internal fun getPriceChartDataModel(
        date : String? = "10/01/2020 13:39",
        price : Double = 10888.62595065162
    ) : PriceChartDataModel = PriceChartDataModel(date, price)

    internal fun getMarketCapChartDataModel(
        date: String? = "10/01/2020 13:39",
        marketCap: Double = 2.0106606260699362E11
    ) : MarketCapChartDataModel = MarketCapChartDataModel(date, marketCap)

    internal fun getCryptocurrencyInfo(
        id : String = "id",
        symbol : String? = "symbol",
        name : String? = "name",
        description : CryptocurrencyDescriptionDataModel? = getCryptocurrencyDescription(),
        links : CryptocurrencyLinksDataModel? = null
    ): CryptocurrencyDataModel = CryptocurrencyDataModel(id, symbol,name, description, links)

     private fun getCryptocurrencyDescription(
        text : String? = "description"
    ): CryptocurrencyDescriptionDataModel = CryptocurrencyDescriptionDataModel(text)

}