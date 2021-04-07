package com.btavares.feature_portfolio.data.model

import com.btavares.feature_portfolio.domain.model.PortfolioCryptocurrencyDomainModel

internal data class PortfolioCryptocurrencyDataModel(
    val id : String?,
    val name : String?,
    val imageUrl : String?,
    val description : String?,
    val percentage : String?,
    val symbol : String?,
    val nativeCurrencyValue : Double?,
    val cryptocurrencyValue : String?)


internal fun PortfolioCryptocurrencyDataModel.toDomainModel(): PortfolioCryptocurrencyDomainModel {
    return PortfolioCryptocurrencyDomainModel(
        id = this.id,
        name = this.name,
        imageUrl = this.imageUrl,
        description = this.description,
        percentage = this.percentage,
        symbol = this.symbol,
        nativeCurrencyValue = this.nativeCurrencyValue,
        cryptocurrencyValue = this.cryptocurrencyValue,
        nativeCurrencySymbol = "")
}