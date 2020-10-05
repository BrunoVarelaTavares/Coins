package com.btavares.feature_coin_detail.data.model


import com.btavares.feature_coin_detail.domain.model.CryptocurrencyDomainModel
import com.squareup.moshi.Json

internal data class CryptocurrencyDataModel (
    @field:Json(name = "id") val id : String,
    @field:Json(name = "symbol") val symbol : String?,
    @field:Json(name = "name") val name : String?,
    @field:Json(name = "description") val description : CryptocurrencyDescriptionDataModel?,
    @field:Json(name = "links") val links : CryptocurrencyLinksDataModel?
)


internal fun CryptocurrencyDataModel.toDomainModel() : CryptocurrencyDomainModel {
    val  description = this.description?.text

    return CryptocurrencyDomainModel(
        id = this.id,
        symbol = this.symbol,
        name = this.name,
        description = description,
        links = this.links?.toDomainModel()
    )

}