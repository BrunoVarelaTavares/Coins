package com.btavares.feature_coin_detail.data.model


import com.btavares.feature_coin_detail.domain.model.NewsCoinsDomainModel
import com.squareup.moshi.Json

internal data class NewsCoinsDataModel (
    @field:Json(name = "_id") val id : String,
    val name : String,
    val tradingSymbol : String,
    val slug : String
)


internal fun NewsCoinsDataModel.toDomainModel() : NewsCoinsDomainModel {

    return NewsCoinsDomainModel(
        id = this.id,
        name = this.name,
        tradingSymbol = this.tradingSymbol,
        slug = this.slug
    )
}