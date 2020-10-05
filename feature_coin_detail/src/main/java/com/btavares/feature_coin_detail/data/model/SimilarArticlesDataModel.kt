package com.btavares.feature_coin_detail.data.model


import com.btavares.feature_coin_detail.domain.model.SimilarArticlesDomainModel
import com.squareup.moshi.Json

internal data class SimilarArticlesDataModel (
    @field:Json(name = "_id") val id : String?,
    val title : String?,
    val publishedAt : String?,
    val sourceDomain : String?,
    val url : String?,
    val thumbnail : String?
)


internal fun SimilarArticlesDataModel.toDomainModel() : SimilarArticlesDomainModel {

    return SimilarArticlesDomainModel(
        id = this.id,
        title = this.title,
        publishedAt = this.publishedAt,
        sourceDomain = this.sourceDomain,
        url = this.url,
        thumbnail = this.thumbnail

    )
}