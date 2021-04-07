package com.btavares.feature_portfolio.domain.model

internal data class PortfolioCryptocurrencyDomainModel(
    val id : String?,
    val name : String?,
    val imageUrl : String?,
    val description : String?,
    val percentage : String?,
    val symbol : String?,
    var nativeCurrencySymbol : String,
    val nativeCurrencyValue : Double?,
    val cryptocurrencyValue : String?

)