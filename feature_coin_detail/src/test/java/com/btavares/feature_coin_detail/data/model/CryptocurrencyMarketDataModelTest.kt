package com.btavares.feature_coin_detail.data.model

import com.btavares.feature_coin_detail.data.DataFixtures
import com.btavares.feature_coin_detail.domain.model.CryptocurrencyMarketDomainModel
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test


class CryptocurrencyMarketDataModelTest  {

    @Test
    fun `data model to CryptocurrencyMarketDomainModel`() {
        // given
        val dataModel = DataFixtures.getCryptocurrencyMarketData()

        // when
        val domainModel = dataModel.toDomainModel()

        // then
        domainModel shouldBeEqualTo CryptocurrencyMarketDomainModel(
            dataModel.id,
            dataModel.symbol,
            dataModel.name,
            dataModel.image,
            dataModel.currentPrice,
            dataModel.marketCap,
            dataModel.marketCapRank,
            dataModel.totalVolume,
            dataModel.highTwentyFourHours,
            dataModel.lowTwentyFourHours,
            dataModel.priceChangeTwentyFourHours,
            dataModel.priceChangePercentageTwentyFourHours,
            dataModel.marketCapChangeTwentyFourHours,
            dataModel.marketCapChangePercentageTwentyFourHours,
            dataModel.circulatingSupply,
            dataModel.totalSupply,
            dataModel.ath,
            dataModel.athChangePercentage,
            dataModel.athDate,
            dataModel.atl,
            dataModel.atlChangePercentage,
            dataModel.atlDate,
            dataModel.lastUpdated,
            nativeCurrencySymbol = ""
        )
    }
}