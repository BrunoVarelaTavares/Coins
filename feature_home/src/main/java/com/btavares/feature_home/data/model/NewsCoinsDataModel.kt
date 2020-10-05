package com.btavares.feature_home.data.model

import com.btavares.feature_home.domain.model.NewsCoinDomainModel
import com.squareup.moshi.Json

internal data class NewsCoinsDataModel (
    @field:Json(name = "_id") val id : String,
    val name : String,
    val tradingSymbol : String,
    val slug : String
)


internal fun NewsCoinsDataModel.toDomainModel() : NewsCoinDomainModel{

    return NewsCoinDomainModel(
        id = this.id,
        name = this.name,
        tradingSymbol = this.tradingSymbol,
        slug = this.slug
    )
}


