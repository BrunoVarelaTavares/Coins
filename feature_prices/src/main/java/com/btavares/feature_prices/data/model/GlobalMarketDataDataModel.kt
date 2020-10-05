package com.btavares.feature_prices.data.model

import com.btavares.feature_prices.domain.model.GlobalMarketDataDomainModel
import com.btavares.library_base.presentation.extension.roundNumber
import com.btavares.library_base.presentation.extension.setPercentageTextViewColor
import com.squareup.moshi.Json

class GlobalMarketDataDataModel (
    @field:Json(name = "active_cryptocurrencies") val activeCryptocurrencies : Int,
    @field:Json(name = "upcoming_icos") val upcoming : Int,
    @field:Json(name = "ongoing_icos") val ongoing : Int,
    @field:Json(name = "ended_icos") val ended : Int,
    @field:Json(name = "markets") val markets : Int,
    @field:Json(name = "market_cap_change_percentage_24h_usd") val marketCapPercentage : Double,
    @field:Json(name = "updated_at") val lastUpdate : Int
)

internal fun GlobalMarketDataDataModel.toDomainModel() : GlobalMarketDataDomainModel{
    var message = ""
    if (this.marketCapPercentage < 0.0)
        message = "Market is down"
    else if (this.marketCapPercentage > 0.0)
        message = "Market is up"


    return GlobalMarketDataDomainModel(
        activeCryptocurrencies = this.activeCryptocurrencies,
        upcoming = this.upcoming,
        ongoing = this.ongoing,
        ended = this.ended,
        markets = this.markets,
        marketCapPercentage = "$message ${roundNumber(this.marketCapPercentage)}",
        lastUpdate = this.lastUpdate,
        percentageTextColor = setPercentageTextViewColor(this.marketCapPercentage)
    )

}