package com.btavares.feature_settings.data.model

import com.btavares.feature_settings.domain.model.NativeCurrencyDomainModel

internal data class NativeCurrencyDataModel(
    val currencyCode : String,
    val currencySymbol: String)


internal fun NativeCurrencyDataModel.toDomainModel(): NativeCurrencyDomainModel {
    return NativeCurrencyDomainModel(
        currencyCode = this.currencyCode,
        currencySymbol = this.currencySymbol
    )
}