package com.btavares.feature_coin_detail.data.model


import com.btavares.feature_coin_detail.domain.model.NewsDomainModel
import com.squareup.moshi.Json

internal data class NewsDataModel (
    @field:Json(name = "_id") val id : String?,
    val hotness : Double?,
    val activityHotness : Double?,
    val primaryCategory : String?,
    val words : Int,
    @field:Json(name = "similarArticles")val similarArticleDataModels : List<SimilarArticlesDataModel>?,
    val coins : List<NewsCoinsDataModel>?,
    val description : String?,
    val publishedAt : String?,
    val title : String?,
    val url : String,
    @field:Json(name = "source") val sourceDataModel : SourceDataModel?,
    val thumbnail : String?,
    val sourceDomain : String?,
    val originalImageUrl : String?
)

internal fun NewsDataModel.toDomainModel() : NewsDomainModel {

    val similarArticles = this.similarArticleDataModels?.map { it.toDomainModel() }
    var newsCoins = this.coins?.map { it.toDomainModel() }

    return NewsDomainModel(
        id = this.id,
        hotness = this.hotness,
        activityHotness = this.activityHotness,
        primaryCategory = this.primaryCategory,
        words = this.words,
        similarArticlesDomainModel = similarArticles?: listOf(),
        coins = newsCoins?: listOf(),
        description = this.description,
        publishedAt = this.publishedAt,
        title = this.title,
        url = this.url,
        sourceDomainModel = this.sourceDataModel?.toDomainModel(),
        thumbnail = this.thumbnail,
        sourceDomain = this.sourceDomain,
        originalImageUrl = this.originalImageUrl

    )
}