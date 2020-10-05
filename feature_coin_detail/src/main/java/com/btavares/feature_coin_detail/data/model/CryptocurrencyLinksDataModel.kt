package com.btavares.feature_coin_detail.data.model

import com.btavares.feature_coin_detail.domain.model.CryptocurrencyLinksDomainModel
import com.squareup.moshi.Json

internal data class CryptocurrencyLinksDataModel (
    @field:Json(name = "homepage") val homepageList : List<String>?,
    @field:Json(name = "blockchain_site") val blockchainSite : List<String>?,
    @field:Json(name = "official_forum_url") val officialForumUrl : List<String>?
)


internal fun  CryptocurrencyLinksDataModel.toDomainModel() : CryptocurrencyLinksDomainModel {

    return CryptocurrencyLinksDomainModel(
        homepageList = this.homepageList,
        blockchainSite = this.blockchainSite,
        officialForumUrl = this.officialForumUrl
    )
}