package com.btavares.feature_home.domain

import com.btavares.feature_home.domain.model.*

object DomainFixtures {

    internal fun getNativeCurrency(
        currencyCode : String = "EUR",
        currencySymbol: String = "€"
    ) : NativeCurrencyDomainModel = NativeCurrencyDomainModel(currencyCode, currencySymbol)

    internal fun getCryptocurrenciesWatchlistIds () = mutableListOf("bitcoin","litecoin", "ethereum")

    internal fun getMarketData(
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
        coins : List<NewsCoinDomainModel> = listOf(getNewsCoinsDataModel()),
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


    private fun getNewsCoinsDataModel(
        id : String? = "id",
        name : String? = "Bitcoin",
        tradingSymbol : String? = "BTC",
        slug : String? = "bitcoin"
    ): NewsCoinDomainModel = NewsCoinDomainModel(id!!,name!!,tradingSymbol!!,slug!!)




}