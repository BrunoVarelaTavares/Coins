package com.btavares.feature_coin_detail.domain.model

internal data class CryptocurrencyDomainModel(
    val id : String,
    val symbol : String?,
    val name : String?,
    val description : String?,
    val links : CryptocurrencyLinksDomainModel?
)