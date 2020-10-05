package com.btavares.feature_coin_detail.data.model

import com.btavares.feature_coin_detail.data.DataFixtures
import com.btavares.feature_coin_detail.domain.model.CryptocurrencyMarketChartDomainModel
import com.btavares.feature_coin_detail.domain.model.MarketCapChartDomainModel
import com.btavares.feature_coin_detail.domain.model.PriceChartDomainModel
import com.btavares.feature_coin_detail.domain.model.TotalVolumeMarketChartDomainModel
import org.amshove.kluent.`should be equal to`
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Test


class CryptocurrencyMarketChartDataModelTest {

    @Test
    fun `data model to MarketDataDomainModel`() {
        // given
        val pricesList = listOf(DataFixtures.getPriceChartDataModel().toDomainModel())
        val marketCap = listOf(DataFixtures.getMarketCapChartDataModel().toDomainModel())
        val totalVolume = listOf(DataFixtures.getTotalVolumeMarketChartDataModel().toDomainModel())
        val dataModel = DataFixtures.getCryptocurrencyMarketChartDataModel()

        // when
        val domainModel = dataModel.toDomainModel()


        // then
        domainModel shouldBeEqualTo CryptocurrencyMarketChartDomainModel(
            prices = pricesList,
            marketCaps = marketCap,
            totalVolumes = totalVolume
        )
    }


    @Test
    fun `convert dataModel prices to PriceChartDomainModel`() {
        // given
        val dataModel = DataFixtures.getPriceChartDataModel()

        // when
        val domainModel = DataFixtures.getCryptocurrencyMarketChartDataModel()
            .convertListToPriceChartDataModel().map { it.toDomainModel() }.first()


        // then
        domainModel `should be equal to`  PriceChartDomainModel(
            dataModel.date,
            dataModel.price

        )
    }


    @Test
    fun `convert dataModel mark caps to MarketCapChartDomainModel`() {
        // given
        val dataModel = DataFixtures.getTotalVolumeMarketChartDataModel()

        // when
        val domainModel : TotalVolumeMarketChartDomainModel = DataFixtures.getCryptocurrencyMarketChartDataModel()
            .convertListToTotalVolumeChartDataModel().map { it.toDomainModel() }.first()


        // then
        domainModel `should be equal to`  TotalVolumeMarketChartDomainModel(
            dataModel.date,
            dataModel.totalVolume

        )
    }


    @Test
    fun `convert dataModel market caps to MarketCapChartDomainModel`() {
        // given
        val dataModel = DataFixtures.getMarketCapChartDataModel()

        // when
        val domainModel = DataFixtures.getCryptocurrencyMarketChartDataModel()
            .convertListToCoinMarketChartDataModel().map { it.toDomainModel() }.first()

        // then
        domainModel `should be equal to`  MarketCapChartDomainModel(
            dataModel.date,
            dataModel.marketCap

        )
    }

}
