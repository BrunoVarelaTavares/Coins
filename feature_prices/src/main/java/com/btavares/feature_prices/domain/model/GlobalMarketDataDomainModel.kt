package com.btavares.feature_prices.domain.model

import com.squareup.moshi.Json

class GlobalMarketDataDomainModel(
    val activeCryptocurrencies : Int,
    val upcoming : Int,
    val ongoing : Int,
    val ended : Int,
    val markets : Int,
    val marketCapPercentage : String?,
    val lastUpdate : Int,
    val percentageTextColor : Int
)