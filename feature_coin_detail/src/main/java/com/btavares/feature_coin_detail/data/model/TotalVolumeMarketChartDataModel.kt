package com.btavares.feature_coin_detail.data.model

import com.btavares.feature_coin_detail.domain.model.TotalVolumeMarketChartDomainModel


internal data class TotalVolumeMarketChartDataModel(
    val date : String?,
    val totalVolume : Double?
)

internal fun TotalVolumeMarketChartDataModel.toDomainModel() : TotalVolumeMarketChartDomainModel {
    return TotalVolumeMarketChartDomainModel(
        date = this.date,
        totalVolume = this.totalVolume
    )
}