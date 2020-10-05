package com.btavares.feature_coin_detail.domain.model

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