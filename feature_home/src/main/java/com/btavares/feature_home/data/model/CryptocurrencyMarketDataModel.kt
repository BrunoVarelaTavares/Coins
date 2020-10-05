package com.btavares.feature_home.data.model

import com.btavares.feature_home.domain.model.CryptocurrencyMarketDomainModel
import com.squareup.moshi.Json

internal data class CryptocurrencyMarketDataModel(
    val id : String?,
    val symbol : String?,
    val name : String?,
    val image : String?,
    @field:Json(name = "current_price") val currentPrice : Double?,
    @field:Json(name = "market_cap") val marketCap : Long?,
    @field:Json(name = "market_cap_rank") val marketCapRank : Long?,
    @field:Json(name = "total_volume") val totalVolume : Double?,
    @field:Json(name = "high_24h") val highTwentyFourHours : Double?,
    @field:Json(name = "low_24h") val lowTwentyFourHours : Double?,
    @field:Json(name = "price_change_24h") val priceChangeTwentyFourHours : Double?,
    @field:Json(name = "price_change_percentage_24h") val priceChangePercentageTwentyFourHours : Double?,
    @field:Json(name = "market_cap_change_24h") val marketCapChangeTwentyFourHours : Double?,
    @field:Json(name = "market_cap_change_percentage_24h") val marketCapChangePercentageTwentyFourHours : Double?,
    @field:Json(name = "circulating_supply") val circulatingSupply : Double?,
    @field:Json(name = "total_supply") val totalSupply : Double?,
    @field:Json(name = "ath") val ath : Double?,
    @field:Json(name = "ath_change_percentage") val athChangePercentage : Double?,
    @field:Json(name = "ath_date") val athDate : String?,
    @field:Json(name = "atl") val atl : Double?,
    @field:Json(name = "atl_change_percentage") val atlChangePercentage : Double?,
    @field:Json(name = "atl_date") val atlDate : String?,
    @field:Json(name = "last_updated") val lastUpdated : String?

    )


internal fun CryptocurrencyMarketDataModel.toDomainModel(): CryptocurrencyMarketDomainModel {
    return CryptocurrencyMarketDomainModel(
        id = this.id,
        symbol = this.symbol,
        name = this.name,
        image = this.image,
        currentPrice = this.currentPrice,
        marketCap = this.marketCap,
        marketCapRank = this.marketCapRank,
        totalVolume = this.totalVolume,
        highTwentyFourHours = this.highTwentyFourHours,
        lowTwentyFourHours = this.lowTwentyFourHours,
        priceChangeTwentyFourHours = this.priceChangeTwentyFourHours,
        priceChangePercentageTwentyFourHours = this.priceChangePercentageTwentyFourHours,
        marketCapChangeTwentyFourHours = this.marketCapChangeTwentyFourHours,
        marketCapChangePercentageTwentyFourHours = this.marketCapChangePercentageTwentyFourHours,
        circulatingSupply = this.circulatingSupply,
        totalSupply = this.totalSupply,
        ath = this.ath,
        athChangePercentage = this.athChangePercentage,
        athDate = this.athDate,
        atl = this.atl,
        atlChangePercentage = this.atlChangePercentage,
        atlDate = this.atlDate,
        lastUpdated = this.lastUpdated,
        nativeCurrencySymbol = "")


}