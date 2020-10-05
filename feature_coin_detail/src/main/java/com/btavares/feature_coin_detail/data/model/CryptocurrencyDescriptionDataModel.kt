package com.btavares.feature_coin_detail.data.model

import com.squareup.moshi.Json

internal data class CryptocurrencyDescriptionDataModel(
    @field:Json(name = "en") val text : String?
)

internal fun CryptocurrencyDescriptionDataModel.toDomainModel() : String? {
    return this.text
}