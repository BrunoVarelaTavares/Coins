package com.btavares.feature_coin_detail.data.repository

import com.btavares.coins.app.data.sharepreference.UserSharePreferences
import com.btavares.feature_coin_detail.data.DataFixtures
import com.btavares.feature_coin_detail.data.model.toDomainModel
import com.btavares.feature_coin_detail.data.remote.service.CoingeckoService
import com.btavares.feature_coin_detail.data.remote.service.CryptoControlNewsService
import com.btavares.feature_database.data.dao.UserDao
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.any
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test


class CoinDetailRepositoryImplTest {

    @MockK
    internal lateinit var coingeckoService : CoingeckoService

    @MockK
    internal lateinit var cryptoControlNewsService : CryptoControlNewsService

    @MockK
    internal lateinit var userSharePreferences: UserSharePreferences

    @MockK
    internal lateinit var userDao: UserDao

    private lateinit var coinDetailRepositoryImpl: CoinDetailRepositoryImpl


    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        coinDetailRepositoryImpl = CoinDetailRepositoryImpl(
            userSharePreferences,
            coingeckoService,
            cryptoControlNewsService,
            userDao
        )
    }

    @Test
    fun `getCryptocurrencyMarketData `(){
        //given
        coEvery {
            coingeckoService.getCryptocurrencyMarketDataAsync(any())
        } returns mutableListOf(DataFixtures.getCryptocurrencyMarketData(), DataFixtures.getCryptocurrencyMarketData())

        //x
        val result = runBlocking { coinDetailRepositoryImpl.getCryptocurrencyMarketData(any())}

        //then
        result shouldBeEqualTo listOf(DataFixtures.getCryptocurrencyMarketData().toDomainModel(),DataFixtures.getCryptocurrencyMarketData().toDomainModel())
    }


    @Test
    fun `getMarketDataAsync returns empty list`(){
        //given
        coEvery {
            coingeckoService.getCryptocurrencyMarketDataAsync(any())
        } returns mutableListOf()

        //when
        val result = runBlocking { coinDetailRepositoryImpl.getCryptocurrencyMarketData(any())}

        //then
        result shouldBeEqualTo emptyList()
    }



    @Test
    fun `getCryptocurrenciesNewsAsync `(){
        //given
        coEvery {
            cryptoControlNewsService.getCryptocurrenciesNewsAsync(any(), any())
        } returns listOf(DataFixtures.getNewsData(), DataFixtures.getNewsData())

        //when
        val result = runBlocking { coinDetailRepositoryImpl.getCryptocurrenciesNewsAsync(any(), any())}

        //then
        result shouldBeEqualTo listOf(DataFixtures.getNewsData().toDomainModel(),DataFixtures.getNewsData().toDomainModel())
    }


    @Test
    fun `getCryptocurrenciesNewsAsync returns empty list`() {
        //given
        coEvery {
            cryptoControlNewsService.getCryptocurrenciesNewsAsync(any(), any())
        } returns emptyList()

        //when
        val result =
            runBlocking { coinDetailRepositoryImpl.getCryptocurrenciesNewsAsync(any(), any()) }

        //then
        result shouldBeEqualTo emptyList()

    }

    //getCryptocurrencyMarketChart

    @Test
    fun `getCryptocurrencyMarketChart `(){
        //given
        val marketChartData = DataFixtures.getCryptocurrencyMarketChartDataModel().toDomainModel()
        coEvery {
            coingeckoService.getCryptocurrencyMarketChartAsync(any(), any(), any())
        } returns DataFixtures.getCryptocurrencyMarketChartDataModel()

        //when
        val result = runBlocking { coinDetailRepositoryImpl.getCryptocurrencyMarketChart(any(), any(), any())}

        //then
        result shouldBeEqualTo marketChartData
    }


    @Test
    fun `getCryptocurrencyMarketChart returns null`() {
        //given

        coEvery {
            coingeckoService.getCryptocurrencyMarketChartAsync(any(), any(), any())
        } returns null

        //when
        val result = runBlocking { coinDetailRepositoryImpl.getCryptocurrencyMarketChart(any(), any(), any())}

        //then
        result shouldBeEqualTo null

    }


    @Test
    fun `getCryptocurrencyMarketChart by range `(){
        //given
        val marketChartData = DataFixtures.getCryptocurrencyMarketChartDataModel().toDomainModel()
        coEvery {
            coingeckoService.getCryptocurrencyMarketChartByRangeAsync(any(), any(), any(), any())
        } returns DataFixtures.getCryptocurrencyMarketChartDataModel()

        //when
        val result = runBlocking { coinDetailRepositoryImpl.getCryptocurrencyMarketChartByRange(any(), any(), any(), any())}

        //then
        result shouldBeEqualTo marketChartData
    }


    @Test
    fun `get CryptocurrencyMarketChart by range returns null`() {
        //given
        coEvery {
            coingeckoService.getCryptocurrencyMarketChartByRangeAsync(any(), any(), any(), any())
        } returns null

        //when
        val result = runBlocking { coinDetailRepositoryImpl.getCryptocurrencyMarketChartByRange(any(), any(), any(), any())}

        //then
        result shouldBeEqualTo null

    }


    @Test
    fun `getCryptocurrencyInfoAsync `(){
        //given
        coEvery {
            coingeckoService.getCryptocurrencyInfoAsync(any())
        } returns DataFixtures.getCryptocurrencyInfo()

        //when
        val result = runBlocking { coinDetailRepositoryImpl.getCryptocurrencyInfoAsync(any())}

        //then
        result shouldBeEqualTo DataFixtures.getCryptocurrencyInfo().toDomainModel()
    }


    @Test
    fun `get getCryptocurrencyInfoAsync returns null`() {
        //given
        coEvery {
            coingeckoService.getCryptocurrencyInfoAsync(any())
        } returns null

        //when
        val result = runBlocking { coinDetailRepositoryImpl.getCryptocurrencyInfoAsync(any())}

        //then
        result shouldBeEqualTo null

    }


}