package com.btavares.feature_coin_detail.data.model

import com.btavares.feature_coin_detail.domain.model.PriceChartDomainModel


internal data class PriceChartDataModel(
    val date : String?,
    val price : Double?
)

internal fun PriceChartDataModel.toDomainModel() : PriceChartDomainModel {

    return PriceChartDomainModel(
        date = this.date,
        price = this.price
    )
}