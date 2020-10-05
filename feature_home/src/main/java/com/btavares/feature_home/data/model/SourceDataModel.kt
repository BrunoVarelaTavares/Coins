package com.btavares.feature_home.data.model

import com.btavares.feature_home.domain.model.SourceDomainModel
import com.squareup.moshi.Json

internal data class SourceDataModel(
    @field:Json(name = "_id") val id : String?,
    val name : String?,
    val url : String?,
    val favicon : String?
)
internal fun SourceDataModel.toDomainModel() : SourceDomainModel {

    return SourceDomainModel(
        id = this.id,
        name = this.name,
        url = this.url,
        favicon = this.favicon
    )
}