package com.btavares.feature_coin_detail.data.model

import com.btavares.feature_coin_detail.domain.model.MarketCapChartDomainModel


internal data class MarketCapChartDataModel(
    val date : String?,
    val marketCap : Double?
)

internal fun MarketCapChartDataModel.toDomainModel() : MarketCapChartDomainModel {

    return MarketCapChartDomainModel(
        date = this.date,
        marketCap = this.marketCap
    )
}