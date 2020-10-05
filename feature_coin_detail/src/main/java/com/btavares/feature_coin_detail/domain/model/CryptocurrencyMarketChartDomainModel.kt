package com.btavares.feature_coin_detail.domain.model

internal data class CryptocurrencyMarketChartDomainModel(
    val prices : List<PriceChartDomainModel>,
    val marketCaps : List<MarketCapChartDomainModel>,
    val totalVolumes : List<TotalVolumeMarketChartDomainModel>
){
    constructor() : this(emptyList(),
        emptyList(),
        emptyList()) {

    }
}