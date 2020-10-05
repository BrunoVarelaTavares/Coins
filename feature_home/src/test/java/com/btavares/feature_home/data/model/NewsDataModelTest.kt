package com.btavares.feature_home.data.model


import com.btavares.feature_home.data.DataFixtures
import com.btavares.feature_home.domain.model.NewsDomainModel
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test

class NewsDataModelTest {
    
     @Test
      fun `data model to NewsDomainModel`() {
        // given
        val dataModel = DataFixtures.getNewsData()

        // when
        val domainModel = dataModel.toDomainModel()

        // then
        domainModel shouldBeEqualTo NewsDomainModel(
         dataModel.id,
         dataModel.hotness,
         dataModel.activityHotness,
         dataModel.primaryCategory,
         dataModel.words,
         dataModel.similarArticleDataModels?.map { it.toDomainModel() } ?: listOf(),
         dataModel.coins?.map { it.toDomainModel() } ?: listOf(),
         dataModel.description,
         dataModel.publishedAt,
         dataModel.title,
         dataModel.url,
         dataModel.sourceDataModel?.toDomainModel(),
         dataModel.thumbnail,
         dataModel.sourceDomain,
         dataModel.originalImageUrl
        )
      }
    
    
}