package com.btavares.feature_coin_detail.domain

import android.graphics.Color
import com.btavares.feature_coin_detail.domain.model.*
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet

object DomainFixtures {

    internal fun getNativeCurrency(
        currencyCode : String = "EUR",
        currencySymbol: String = "€"
    ) : NativeCurrencyDomainModel = NativeCurrencyDomainModel(currencyCode, currencySymbol)

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
        lastUpdated : String? = "2020-09-24T11:08:30.079Z",
        nativeCurrencySymbol : String = "€"
    ): CryptocurrencyMarketDomainModel = CryptocurrencyMarketDomainModel(
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
        lastUpdated,
        nativeCurrencySymbol)


    internal fun getNewsData(
        id : String? = "id",
        hotness : Double? = 0.0,
        activityHotness : Double? = 0.0,
        primaryCategory : String? = "primaryCategory",
        words : Int = 0,
        similarArticlesDomainModel : List<SimilarArticlesDomainModel>? = listOf(),
        coins : List<NewsCoinsDomainModel> = listOf(getNewsCoinsDomainModel()),
        description : String? = "Description",
        publishedAt : String? = "publishedAt",
        title : String? = "Title",
        url : String = "url",
        sourceDomainModel : SourceDomainModel? = null,
        thumbnail : String? = "thumbnail",
        sourceDomain : String? = "sourceDomain",
        originalImageUrl : String? = "originalImageUrl"
    ) : NewsDomainModel = NewsDomainModel(
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


    private fun getNewsCoinsDomainModel(
        id : String? = "id",
        name : String? = "Bitcoin",
        tradingSymbol : String? = "BTC",
        slug : String? = "bitcoin"
    ): NewsCoinsDomainModel = NewsCoinsDomainModel(id!!,name!!,tradingSymbol!!,slug!!)


    internal fun getCryptocurrencyMarketChartDomainModel(
        prices : List<PriceChartDomainModel> = listOf(getPriceChartDomainModel()),
        market_caps : List<MarketCapChartDomainModel> = listOf(getMarketCapChartDomainModel()),
        total_volumes : List<TotalVolumeMarketChartDomainModel> = listOf(getTotalVolumeMarketChartDomainModel())
    ): CryptocurrencyMarketChartDomainModel = CryptocurrencyMarketChartDomainModel(prices,market_caps, total_volumes)

    internal fun getTotalVolumeMarketChartDomainModel(
        date: String? = "10/01/2020 13:39",
        totalVolume: Double = 2.0106606260699362E11
    ) : TotalVolumeMarketChartDomainModel = TotalVolumeMarketChartDomainModel(date, totalVolume)

    internal fun getPriceChartDomainModel(
        date : String? = "10/01/2020 13:39",
        price : Double = 10888.62595065162
    ) : PriceChartDomainModel = PriceChartDomainModel(date, price)

    internal fun getMarketCapChartDomainModel(
        date: String? = "10/01/2020 13:39",
        marketCap: Double = 2.0106606260699362E11
    ) : MarketCapChartDomainModel = MarketCapChartDomainModel(date, marketCap)

    internal fun getCryptocurrencyInfo(
        id : String = "id",
        symbol : String? = "symbol",
        name : String? = "name",
        description : String? = "description",
        links : CryptocurrencyLinksDomainModel? = null
    ): CryptocurrencyDomainModel = CryptocurrencyDomainModel(id, symbol,name, description, links)

}