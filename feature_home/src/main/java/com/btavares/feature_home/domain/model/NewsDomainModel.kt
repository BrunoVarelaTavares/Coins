package com.btavares.feature_home.domain.model

internal data class NewsDomainModel(
    val id : String?,
    val hotness : Double?,
    val activityHotness : Double?,
    val primaryCategory : String?,
    val words : Int,
    val similarArticlesDomainModel : List<SimilarArticlesDomainModel>?,
    val coins : List<NewsCoinDomainModel>?,
    val description : String?,
    val publishedAt : String?,
    val title : String?,
    val url : String,
    val sourceDomainModel : SourceDomainModel?,
    val thumbnail : String?,
    val sourceDomain : String?,
    val originalImageUrl : String?
)