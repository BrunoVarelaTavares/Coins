package com.btavares.feature_home.data.repository

import com.btavares.coins.app.data.sharepreference.UserSharePreferences
import com.btavares.feature_database.data.dao.UserDao
import io.mockk.impl.annotations.MockK
import com.btavares.feature_home.data.remote.service.*
import com.btavares.feature_home.data.DataFixtures
import com.btavares.feature_home.data.model.toDomainModel

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.any
import org.amshove.kluent.shouldBeEqualTo
import org.junit.Before
import org.junit.Test


class HomeRepositoryImplTest {

    @MockK
    internal lateinit var coingeckoService : CoingeckoService

    @MockK
    internal lateinit var cryptoControlNewsService : CryptoControlNewsService

    @MockK
    internal lateinit var userSharePreferences: UserSharePreferences

    @MockK
    internal lateinit var userDao: UserDao

    private lateinit var homeRepositoryImpl: HomeRepositoryImpl

    @Before
    fun setUp(){
        MockKAnnotations.init(this)
        homeRepositoryImpl = HomeRepositoryImpl(
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
            coingeckoService.getMarketDataAsync(any(),any())
        } returns listOf(DataFixtures.getCryptocurrencyMarketData(), DataFixtures.getCryptocurrencyMarketData())

        //when
        val result = runBlocking { homeRepositoryImpl.getMarketDataAsync(any(), any())}

        //then
        result shouldBeEqualTo listOf(DataFixtures.getCryptocurrencyMarketData().toDomainModel(),DataFixtures.getCryptocurrencyMarketData().toDomainModel())
    }


    @Test
    fun `getMarketDataAsync returns empty list`(){
        //given
        coEvery {
            coingeckoService.getMarketDataAsync(any(),any())
        } returns emptyList()

        //when
        val result = runBlocking { homeRepositoryImpl.getMarketDataAsync(any(), any())}

        //then
        result shouldBeEqualTo emptyList()
    }



    @Test
    fun `getCryptocurrenciesNewsAsync `(){
        //given
        coEvery {
            cryptoControlNewsService.getCryptocurrenciesNewsAsync(any())
        } returns listOf(DataFixtures.getNewsData(), DataFixtures.getNewsData())

        //when
        val result = runBlocking { homeRepositoryImpl.getCryptocurrenciesTopNewsAsync()}

        //then
        result shouldBeEqualTo listOf(DataFixtures.getNewsData().toDomainModel(),DataFixtures.getNewsData().toDomainModel())
    }


    @Test
    fun `getCryptocurrenciesNewsAsync returns empty list`(){
        //given
        coEvery {
            cryptoControlNewsService.getCryptocurrenciesNewsAsync(any())
        } returns emptyList()

        //when
        val result = runBlocking { homeRepositoryImpl.getCryptocurrenciesTopNewsAsync()}

        //then
        result shouldBeEqualTo emptyList()
    }



}